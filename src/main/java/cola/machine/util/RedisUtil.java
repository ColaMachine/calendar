package cola.machine.util;


import cola.machine.config.Config;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {
    
    /** Redis服务器IP */
    private static String ADDR =Config.getInstance().getCache().getRedis().getAddr();
    
    /** Redis的端口号 */
    private static int PORT = Config.getInstance().getCache().getRedis().getPort();
    
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
            config.setMaxTotal(200);
            config.setMaxIdle(MAX_IDLE);
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
            jedisPool.returnResource(jedis);
        }
    }
    
    /** 
     * 获取数据 
     *  
     * @param key 
     * @return 
     */  
    public static String get(String key){  
        String value = null;  
          
        JedisPool pool = null;  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            value = jedis.get(key);  
        } catch (Exception e) {  
            //释放redis对象  
            pool.returnBrokenResource(jedis);  
            e.printStackTrace();  
        } finally {  
            //返还到连接池  
            returnResource( jedis);  
        }  
          
        return value;  
    }  
    
    /** 
     * 获取数据 
     *  
     * @param key 
     * @return 
     */  
    public static void set(String key,String value){  
        JedisPool pool = null;  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            jedis.set(key,value);  
        } catch (Exception e) {  
            //释放redis对象  
            pool.returnBrokenResource(jedis);  
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
    public static void set(String key,String value,int seconds){  
        JedisPool pool = null;  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            jedis.setex(key,seconds,value);  
        } catch (Exception e) {  
            //释放redis对象  
            pool.returnBrokenResource(jedis);  
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
        JedisPool pool = null;  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            jedis.del(key);  
        } catch (Exception e) {  
            //释放redis对象  
            pool.returnBrokenResource(jedis);  
            e.printStackTrace();  
        } finally {  
            //返还到连接池  
            returnResource( jedis);  
        }  
          
    }  
}