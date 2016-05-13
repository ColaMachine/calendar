package core.redis.annotation;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * Created by dozen.zhang on 2016/5/13.
 */

public abstract class JedisManageSupport {
    ThreadLocal<Jedis> jedisHolder = new ThreadLocal<Jedis>();

    public final Jedis getJedis() {
        return jedisHolder.get();
    }

    public final void setJedis(Jedis jedis) {
        jedisHolder.set(jedis);
    }

    /**
     * 如果某个键不同单位之间也不会重复，可以使用这个方法生成redis的键
     */
    public final byte[] assemKey(String baseKey) {
        Assert.isTrue(StringUtils.isNotBlank(baseKey), "参数不能为空");
        return baseKey.getBytes();
    }

    /**
     * 根据tableName+prefix 构造唯一key与assemKey(String baseKey, String tableName)
     * 规则一致
     */
/*
    public final byte[] assemKeyByPrefix(String tableName, String baseKey) {
        Assert.isTrue(StringUtils.isNotBlank(baseKey), "参数不能为空");
        Assert.isTrue(StringUtils.isNotBlank(tableName), "参数不能为空");
        UnitInfo unit = WebService.getUnitInfo();
        Assert.isTrue(unit != null, "单位信息获取不到");
        return (tableName + "-" + unit.getPrefix() + "-" + baseKey).getBytes();
    }
*/

    /**
     *
     * 不同前缀的表中可能有相同的键，同一个表中也可能是有重复的baseKey时，用这个生成redis的key 比如 用户信息表的
     * username字段，不同的用户信息表允许重复的username，mooc_t_userinfo
     * 也允许有相同的账号，所以生成redis的key时，需要用到单位的mooc_school 放入redis中
     */
 /*   public final byte[] assemKeyByFid(String tableName, String baseKey) {
        UnitInfo unit = WebService.getUnitInfo();
        Assert.isTrue(unit != null, "单位信息获取不到");
        return (tableName + "-" + unit.getMoocSchool() + "-" + baseKey).getBytes();
    }*/

}