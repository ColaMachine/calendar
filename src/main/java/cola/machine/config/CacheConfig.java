package cola.machine.config;

/**
 * @author dozen.zhang
 *
 */
public class CacheConfig {

    /**
     * redis config
     */
    private RedisConfig redis = new RedisConfig();
    /**
     * ehcache config 
     */
    private EhcacheConfig ehcache = new EhcacheConfig();

    public RedisConfig getRedis() {
        return redis;
    }

    public EhcacheConfig getEhcache() {
        return ehcache;
    }
}
