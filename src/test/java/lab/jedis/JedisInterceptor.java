package lab.jedis;

import org.apache.log4j.Logger;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.lang.reflect.Method;

/**
 * Created by dozen.zhang on 2016/5/13.
 */
class JedisInterceptor implements MethodInterceptor {

    static final Logger logger = Logger.getLogger(JedisInterceptor.class);

    ShardedJedisPool pool;

    Object src;

    public JedisInterceptor(ShardedJedisPool pool, Object src) {
        this.pool = pool;
        this.src = src;
    }

    @Override
    public Object intercept(Object target, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        if (target instanceof JedisManageSupport) {
            if (this.isDeclaredMethod(target, method)) {
                ShardedJedis jedis = null;
                try {
                    JedisManageSupport support = (JedisManageSupport) src;
                    jedis = pool.getResource();
                    support.setShardedJedis(jedis);
// logger.debug("调用之前注入jedis对象,method:" + method);
/**
 * 下面代码可以使用 method.invoke(src,arguments)。 不能使用
 * methodProxy.invokeSuper(target,arguments);
 * 因为A类中用Autowired注入的属性，生成代理的子类B后,因为子类B是新建的类。从父类继承的属性没有被初始化，
 * 使用methodProxy.invokeSuper（）执行是，会报空指针异常.
 */
                    result = methodProxy.invoke(src, arguments);
                    support.setShardedJedis(null);
                }
                catch (Exception e) {
                    pool.returnBrokenResource(jedis);
                    e.printStackTrace();
                }
                finally {
                    if (jedis != null) {
                        pool.returnResource(jedis);
                    }
// logger.debug("调用之后归还jedis对象,method:" + method);
                }
            }
            else {
                result = methodProxy.invoke(src, arguments);
            }
        }
        else {
            throw new Exception("使用该代理必须继承JedisManageSupport");
        }
        return result;
    }

    /**
     * 是否是target类本身定义的非私有的方法，还是继承的父类
     * @return true是target自己类的并且不是私有的的，
     */
    private boolean isDeclaredMethod(Object target, Method arg1) {
        Method temp = null;
        try {
            temp = target.getClass().getDeclaredMethod(arg1.getName(), arg1.getParameterTypes());
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
/**
 * 不为null，并且是非私有的，返回true
 */
        if (temp != null) {

            return true;
        }
        else {
            return false;
        }
    }
}