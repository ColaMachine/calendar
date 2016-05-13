package lab.jedis;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by dozen.zhang on 2016/5/13.
 */
public class JedisBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    ShardedJedisPool shardedJedisPool;

    static final Logger logger = Logger.getLogger(JedisBeanPostProcessor.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof JedisManageSupport) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(bean.getClass());
            enhancer.setCallback(new JedisInterceptor(shardedJedisPool, bean));
            Object targetBean = enhancer.create();
            return targetBean;
        }
        else {
            return bean;
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}