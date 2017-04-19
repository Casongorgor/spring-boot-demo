package com.cason.demo.Service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jingle.huang on 2017/4/18.
 */
@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private StringRedisTemplate redisTemplate;
    private static String REDISCODE = "utf-8";
    private static Long NOLIVETIME = 0L;
    private static String SUCCESS = "SUCCESS";
    // redis 请求锁超时时间
    private static int timeoutMsecs = 10000;
    private static int timeoutUser = 5000;
    // redis 锁过期时间
    private static int expireMsecs = 5000;
    private static int expireUser = 1000;

    private static String lockKey = "SHOPRECORDREDIS_LOCKKEY";

    /*
     * (non-Javadoc)
     *
     * @see redis.RedisService#trim(java.lang.String, long, int)
     */

    public void trim(String key, long start, int end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /*
     * (non-Javadoc)
     *
     * @see redis.RedisService#set(java.lang.String, long, java.lang.String)
     */

    public void set(String key, long index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /*
     * (non-Javadoc)
     *
     * @see redis.RedisService#index(java.lang.String, long)
     */

    public String index(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /*
     * (non-Javadoc)
     *
     * @see redis.RedisService#remove(java.lang.String, long, java.lang.String)
     */

    public void remove(String key, long i, String value) {
        redisTemplate.opsForList().remove(key, i, value);
    }

    /*
     * (non-Javadoc)
     *
     * @see redis.RedisService#range(java.lang.String, int, int)
     */

    public List<String> range(String key, int start, int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * @param key
     * @return
     * @Description: 出队
     * @Author:nanoha
     * @see:
     * @since: 1.0
     * @Create Date:2016-9-21
     */

    public String outQueue(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see redis.RedisService#inQueue(java.lang.String, java.lang.String)
     */

    public Long inQueue(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /*
     * (non-Javadoc)
     *
     * @see redis.RedisService#pop(java.lang.String)
     */

    public String pop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see redis.RedisService#push(java.lang.String, java.lang.String)
     */

    public Long push(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /*
     * (non-Javadoc)
     *
     * @see redis.RedisService#del(java.lang.String)
     */

    public long del(final String key) {

        return redisTemplate.execute(new RedisCallback<Long>() {


            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(key.getBytes());
            }
        });
    }

    /**
     * @param key
     * @param value
     * @param liveTime
     * @Description: 添加key，value，设置存活时间
     * @Author:nanoha
     * @see:
     * @since: 1.0
     * @Create Date:2016年9月20日
     */

    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback<Long>() {


            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * @param key
     * @param value
     * @param liveTime
     * @Description: 添加key，value，设置存活时间
     * @Author:nanoha
     * @see:
     * @since: 1.0
     * @Create Date:2016年9月20日
     */

    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    /**
     * @param key
     * @param value
     * @Description: 添加key，value
     * @Author:nanoha
     * @see:
     * @since: 1.0
     * @Create Date:2016年9月20日
     */

    public void set(String key, String value) {
        this.set(key, value, NOLIVETIME);
    }

    /**
     * @param key
     * @param value
     * @Description: 序列化
     * @Author:nanoha
     * @see:
     * @since: 1.0
     * @Create Date:2016年9月20日
     */

    public void set(byte[] key, byte[] value) {
        this.set(key, value, NOLIVETIME);
    }

    /**
     * @param key
     * @return
     * @Description: 获取redis value (String)
     * @Author:nanoha
     * @see:
     * @since: 1.0
     * @Create Date:2016年9月20日
     */

    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {


            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return new String(connection.get(key.getBytes()), REDISCODE);
                } catch (Exception e) {
                    logger.error("redis get---", e);
                }
                return "";
            }
        });
    }

    /**
     * @param pattern
     * @return
     * @Description: 通过正则匹配keys
     * @Author:nanoha
     * @see:
     * @since: 1.0
     * @Create Date:2016年9月20日
     */

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * @param key
     * @return
     * @Description: 检查key是否已经存在
     * @Author:nanoha
     * @see:
     * @since: 1.0
     * @Create Date:2016年9月20日
     */

    public boolean exists(final String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {


            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    /**
     * @return
     * @Description: 清空redis 所有数据
     * @Author:nanoha
     * @see:
     * @since: 1.0
     * @Create Date:2016年9月20日
     */

    public String flushDB() {
        return redisTemplate.execute(new RedisCallback<String>() {


            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return SUCCESS;
            }
        });
    }

    /**
     * @return
     * @Description: 查看redis里有多少数据
     * @Author:nanoha
     * @see:
     * @since: 1.0
     * @Create Date:2016年9月20日
     */

    public long size() {
        return redisTemplate.execute(new RedisCallback<Long>() {


            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * @return
     * @Description: 检查是否连接成功
     * @Author:nanoha
     * @see:
     * @since: 1.0
     * @Create Date:2016年9月20日
     */

    public String ping() {
        return redisTemplate.execute(new RedisCallback<String>() {


            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }

    /*
     * (non-Javadoc)
     *
     * @see redis.RedisService#getRedisTemplate()
     */

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }


    public void putHash(String key, Object field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);

    }


    public Object getHash(String key, Object field) {
        return redisTemplate.opsForHash().get(key, field);
    }


    public Map entries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    public Long deleteHash(String key, Object field) {
        return redisTemplate.opsForHash().delete(key, field);
    }


    @SuppressWarnings("unchecked")
    <HK, HV> Map<HK, HV> deserializeHashMap(Map<byte[], byte[]> entries) {
        // connection in pipeline/multi mode
        if (entries == null) {
            return null;
        }

        Map<HK, HV> map = new HashMap<HK, HV>(entries.size());

        for (Map.Entry<byte[], byte[]> entry : entries.entrySet()) {
            map.put((HK) deserializeHashKey(entry.getKey()), (HV) deserializeHashValue(entry.getValue()));
        }

        return map;
    }

    @SuppressWarnings({"unchecked"})
    <HK> HK deserializeHashKey(byte[] value) {
        if (redisTemplate.getHashKeySerializer() == null) {
            return (HK) value;
        }
        return (HK) redisTemplate.getHashKeySerializer().deserialize(value);
    }

    @SuppressWarnings("unchecked")
    <HV> HV deserializeHashValue(byte[] value) {
        if (redisTemplate.getHashValueSerializer() == null) {
            return (HV) value;
        }
        return (HV) redisTemplate.getHashValueSerializer().deserialize(value);
    }


    public void watch(String key) {
        redisTemplate.watch(key);
    }


    public void unWathch() {
        redisTemplate.unwatch();

    }


    public void multi() {
        redisTemplate.multi();

    }


    public List<Object> exec() {
        return redisTemplate.exec();
    }


    public boolean setNX(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {


            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(key.getBytes(), value.getBytes());
            }
        });
    }


    public String getSet(final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<String>() {


            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    return new String(connection.getSet(key.getBytes(), value.getBytes()), REDISCODE);
                } catch (Exception e) {
                    logger.error("getSet---", e);
                }
                return "";
            }
        });
    }


    public synchronized boolean acquire() throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires); // 锁到期时间

            if (setNX(lockKey, expiresStr)) {
                // lock acquired
                return true;
            }

            String currentValueStr = get(lockKey); // redis里的时间
            if (!StringUtils.isEmpty(currentValueStr) && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                // 判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
                // lock is expired

                String oldValueStr = getSet(lockKey, expiresStr);
                // 获取上一个锁到期时间，并设置现在的锁到期时间，
                // 只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    // 如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    // lock acquired
                    return true;
                }
            }
            timeout -= 100;
            Thread.sleep(100);
        }
        return false;
    }


    public boolean acquireLock(String str) throws InterruptedException {
        int timeout = timeoutUser;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireUser + 1;
            String expiresStr = String.valueOf(expires); // 锁到期时间
            if (setNX(str, expiresStr)) {
                return true;
            }
            String currentValueStr = get(str); // redis里的时间
            if (!StringUtils.isEmpty(currentValueStr) && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                String oldValueStr = getSet(str, expiresStr);
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    return true;
                }
            }
            timeout -= 100;
            Thread.sleep(100);
        }
        return false;
    }


    public void releaseLock() {
        String currentValueStr = get(lockKey); // redis里的时间
        if (!StringUtils.isEmpty(currentValueStr) && System.currentTimeMillis() < Long.parseLong(currentValueStr)) {
            del(lockKey);
        }

    }

    public void releaseLock(String str) {
        String currentValueStr = get(str); // redis里的时间
        if (!StringUtils.isEmpty(currentValueStr) && System.currentTimeMillis() < Long.parseLong(currentValueStr)) {
            del(str);
        }
    }

}
