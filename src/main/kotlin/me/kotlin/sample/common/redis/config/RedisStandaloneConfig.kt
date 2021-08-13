package me.kotlin.sample.common.redis.config

import me.kotlin.sample.common.redis.properties.RedisStandaloneProperties
import me.kotlin.sample.member.domain.Member
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.CacheKeyPrefix
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

/**
 * Created by LYT to 2021/08/11
 */
@Configuration
class RedisStandaloneConfig(
    val redisProperties: RedisStandaloneProperties
) {
    /**
     * Redis Connection 설정
     * @return LettuceConnectionFactory
     */
    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val configuration = RedisStandaloneConfiguration()
        configuration.hostName = redisProperties.host
        configuration.port = redisProperties.port
//        configuration.password = RedisPassword.of(redisProperties.password)
        return LettuceConnectionFactory(configuration)
    }

    /**
     * RedisTemplate 설정
     * @return RedisTemplate<String, Member>
     */
    @Primary
    @Bean(name = ["memberRedisTemplate"])
    fun memberRedisTemplate(): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.setConnectionFactory(redisConnectionFactory())

        // 아래의 설정값이 없으면 스프링에서 조회할 때는 값이 정상으로 보이지만 redis-cli로 조회하면 `xec\x83\x98\xed\x94\x8c1` 이런식으로 보여짐
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()

        // Hash Operation 사용 시
//        redisTemplate.hashKeySerializer = StringRedisSerializer()
//        redisTemplate.hashValueSerializer = StringRedisSerializer()

        return redisTemplate
    }

    /**
     * Redis Cache를 관리
     * @return RedisCacheManager
     */
    @Primary
    @Bean(name = ["memberManager"])
    fun memberManager(): RedisCacheManager {
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer()))   // json 형식으로 value 저장
            .computePrefixWith(CacheKeyPrefix.simple())             // key앞에 '::'를 삽입
            .disableCachingNullValues()                             // null 값 금지
            .entryTtl(Duration.ofHours(redisProperties.expire))     // 캐싱 유지 시간 설정

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory())
            .cacheDefaults(configuration)
            .build()
    }
}