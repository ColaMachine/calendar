package com.dozenx.util;


import com.dozenx.core.config.Config;
import com.dozenx.web.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public final class RedisUtil {
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    /** Redis服务器IP */
    private static String ADDR = ConfigUtil.getConfig("cache.redis.ip");// Config.getInstance().getCache().getRedis().getAddr();

    /** Redis的端口号 */
    private static int PORT =  Integer.valueOf(ConfigUtil.getConfig("cache.redis.port"));//Config.getInstance().getCache().getRedis().getPort();

    /** 访问密码 */
    private static String AUTH =Config.getInstance().getCache().getRedis().getAuth();

    /** 可用连接实例的最大数目，默认值为8；
     *如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
     */
    private static int MAX_ACTIVE = Config.getInstance().getCache().getRedis().getMaxActive();

    /** 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。 */
    private static int MAX_IDLE = Config.getInstance().getCache().getRedis().getMaxIdle();

    /** 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException； */
    private static int MAX_WAIT = Config.getInstance().getCache().getRedis().getMaxWait();
    /** 连接超时 */
    private static int TIMEOUT = Config.getInstance().getCache().getRedis().getTimeout();

    /** 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的； */
    private static boolean TEST_ON_BORROW = true;
    /** 声明 */
    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
           // config.setMaxActive(MAX_ACTIVE);
            config.setMaxTotal(1);
            config.setMaxIdle(1);

            config.setMaxWaitMillis(1000);
           // config.setSoftMinEvictableIdleTimeMillis();
            config.setMinEvictableIdleTimeMillis(3000);
            config.setSoftMinEvictableIdleTimeMillis(3000);
          //  config.setMaxWait(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     * @return Jedis
     */
    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param jedis 释放jedis资源
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
          //  jedis.close();
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
  /*  public static String get(String key){
        String value = null;

      ;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            jedis.close();;
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource( jedis);
        }

        return value;
    }  */

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
   /* public static void set(String key,String value){

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key,value);
        } catch (Exception e) {
            //释放redis对象
            jedis.close();;
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource( jedis);
        }

    }  */

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static void setex(String key,String value,int seconds){
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key,seconds,value);

        } catch (Exception e) {
            //释放redis对象
            jedis.close();;
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource( jedis);
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static void del(String key){
        if(StringUtil.isNotBlank(key)) {
            Jedis jedis = null;
            boolean success =true;
            try {
                jedis = jedisPool.getResource();

                jedis.del(key);
            } catch (Exception e) {
                //释放redis对象
                if(jedis != null){
                    jedis.close();;
                }
                e.printStackTrace();
                throw e;
            } finally {
                if(success && jedis != null){
                    jedis.close();
                }
                //返还到连接池

            }
        }
    }


    public static Set<String> hkeys(String key) {
        Set<String> retValue = null;
        boolean success =true;
        if (StringUtil.isNotBlank(key)) {
            Jedis jedis =null;
            try {jedis= jedisPool.getResource();
                Set<String> result = jedis.hkeys(key);
                logger.debug("Redis.hkeys : result({}).", result);
            } catch (Exception e) {
                success  = false;
                if(jedis != null){
                    jedis.close();;
                }
                logger.error("redis",e);
                throw e;
            }finally{
                if(success && jedis != null){
                    jedis.close();
                }
            }
        }
        return retValue;
    }

    public static void expire(String key, int seconds) {
        if (StringUtil.isNotBlank(key)) {
            Jedis jedis=null;
            boolean success =true;
            try { jedis= jedisPool.getResource();
                Long result = jedis.expire(key, seconds);
                logger.debug("Redis.expire result for key: key({}), result({}).",key, result);
            } catch (Exception e) {
                success  = false;
                if(jedis != null){
                    jedis.close();;
                }
                logger.error("redis",e);
                throw e;
            }finally{
                if(success && jedis != null){
                    jedis.close();
                }
            }
        }
    }

	/*public static InputStream readProperties() throws FileNotFoundException {
		// Properties props = new Properties();
		InputStream in = ClassLoader.getSystemResourceAsStream(configFilePath);
		if (in == null) {
			try {
				String filename = new URI(AccessAuth.class.getClassLoader()
						.getResource(configFilePath).toString()).toString();
				File file = new File(filename.replace("file:", ""));
				// System.out.println("File: " + file.getAbsolutePath());
				in = new FileInputStream(file);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return in;
	}*/

    /**
     * hset + 还连接
     * @param key
     * @param field
     * @param value
     * @author dozen.zhang
     */
    public static void hset(String key ,String field,String value){
        Jedis jedis = null;
        boolean success =true;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key,field,value);
        } catch (Exception e) {
            success  = false;
            if(jedis != null){
                jedis.close();
            }
            logger.error("redis",e);
            throw e;
        }finally{
            if(success && jedis != null){
                jedis.close();
            }
        }
    }
    /**
     * 强制归还资源的hget
     * @param key
     * @param field
     * @author dozen.zhang
     */
    public static String hget(String key ,String field){
        Jedis jedis = null;
        String value=null;
        boolean success =true;
        try {
            jedis = jedisPool.getResource();
            value=jedis.hget(key,field);
        } catch (Exception e) {
            success  = false;
            if(jedis != null){
                jedis.close();;
            }
            logger.error("redis",e);
            throw e;
        }finally{
            if(success && jedis != null){
                jedis.close();
            }
        }
        return value;
    }
    /**
     * 获取数据 归还资源
     *
     * @param key
     * @return
     */
    public static String get(String key){
        String value = null;
        Jedis jedis = null;
        boolean success =true;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            success  = false;
            if(jedis != null){
                jedis.close();;
            }
            logger.error("redis",e);
            throw e;
        }finally{
            if(success && jedis != null){
                jedis.close();
            }
        }

        return value;
    }

    public static synchronized Jedis getResource() throws Exception {
        //logger.info("jedis pool:" + (pool == null));
        Jedis temp =  null;
        for (int i = 0; i < 3; i ++) {
            temp = jedisPool.getResource();

            if (temp != null) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                //不处理
            }

        }

        if (temp == null) {
            throw new Exception("RedisUtil.getResource ,获取jedis 为null");
        }

        return temp;
    }
    public static void hdel(String key, String field) throws Exception {
        if (StringUtil.isNotBlank(key) && StringUtil.isNotBlank(field)) {
            Jedis jedis=null;
            boolean success =true;
            try {
                jedis= getResource();
                long result = jedis.hdel(key, field);
                logger.debug("Redis.hdel set: result({}).", result);
            } catch (Exception e) {
                success  = false;
                if(jedis != null){
                    jedis.close();
//	                pool.returnBrokenResource(jedis);
                }
                logger.error("redis",e);
                throw e;
            }finally{
                if(success && jedis != null){
                    jedis.close();
//	                pool.returnResource(jedis);
                }
            }
        }
    }
    /**
     * 获取数据 归还连接
     *
     * @param key
     * @return
     */
    public static void set(String key,String value){
        Jedis jedis = null;
        boolean success = true;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key,value);
        } catch (Exception e) {
            success  = false;
            if(jedis != null){
                jedis.close();
            }
            logger.error("redis",e);
            throw e;
        }finally{
            if(success && jedis != null){
                jedis.close();
            }
        }

    }
    /*
         * 释放redis对象。
         */
    public static void returnBrokenResource(Jedis resource) {
        jedisPool.returnBrokenResource(resource);
    }

    public static void  main (String args[]){
        int i=0;
        while(true){
            try {
                System.out.println("begin");
                Jedis jedis = RedisUtil.getJedis();
                if (jedis != null) {
                    jedis.set("1", i++ + "");
                } else {
                    System.out.println("jedis is null");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println(DateUtil.formatToString(new java.util.Date(),"yyyy-MM-dd hh:mm:ss"));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 自增
     *
     */


    public static Long incr(String key) throws Exception{
        Long value = null;
        Jedis jedis = null;
        boolean success =true;
        try {
            jedis = getResource();
            value = jedis.incr(key);
        } catch (Exception e) {
            success  = false;
            if(jedis != null){
                jedis.close();
//                pool.returnBrokenResource(jedis);
            }
            logger.error("redis",e);
            throw e;
        }finally{
            if(success && jedis != null){
                jedis.close();
//                pool.returnResource(jedis);
            }
        }

        return value;
    }
}