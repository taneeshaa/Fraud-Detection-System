package tanz.inc.fraudengine.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class VelocityService {
    private final ReactiveRedisTemplate<String, String> redisTemplate;

    public Mono<Long> getTransactionCount(String userId){
        String key = "velocity:" + userId;
        return redisTemplate.opsForValue()
                .increment(key)
                .flatMap(count -> {
                    if(count == 1){
                        return redisTemplate.expire(key, Duration.ofMinutes(1)).thenReturn(count);
                    }
                    return Mono.just(count);
                });
    }
}
