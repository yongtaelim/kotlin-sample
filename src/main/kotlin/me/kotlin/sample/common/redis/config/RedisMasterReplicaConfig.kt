//package me.kotlin.sample.common.redis.config
//
//import io.lettuce.core.ReadFrom
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
//
///**
// * Created by LYT to 2021/08/11
// */
//@Configuration
//class RedisMasterReplicaConfig {
//
//    private val primaryHost = ""
//    private val primaryPort = 0
//    private val readerHost = ""
//    private val readerPort = 0
//
//    /**
//     * Redis Connection 설정
//     *   - Write: Master Redis
//     *   - Read: Replica Redis
//     * @return LettuceConnectionFactory
//     */
//    @Bean
//    fun redisConnectionFactory(): LettuceConnectionFactory {
//        val elastiCache = RedisStaticMasterReplicaConfiguration(primaryHost, primaryPort)
//        elastiCache.addNode(readerHost, readerPort)
//
//        val clientConfig = LettuceClientConfiguration.builder()
//            .readFrom(ReadFrom.REPLICA_PREFERRED)
//            .build()
//
//        return LettuceConnectionFactory(elastiCache, clientConfig)
//    }
//}