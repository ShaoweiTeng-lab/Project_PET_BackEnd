package project_pet_backEnd.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void cacheQueryResults(Object results, String key, int ttl) {
        redisTemplate.opsForList().leftPushAll(key, results);
        redisTemplate.expire(key, ttl, TimeUnit.MINUTES);
    }

    public List<Object> getCachedQueryResults(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }
}
