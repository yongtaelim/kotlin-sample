package me.kotlin.sample.common.redis.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * Created by LYT to 2021/08/11
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.redis")
class RedisStandaloneProperties(
    val host: String,
    val port: Int,
//    val password: String,
    val expire: Long
)