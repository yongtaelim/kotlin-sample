package me.kotlin.sample.member.repository.custom

import com.fasterxml.jackson.databind.ObjectMapper
import me.kotlin.sample.common.redis.properties.RedisStandaloneProperties
import me.kotlin.sample.member.domain.Member
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

/**
 * Created by LYT to 2021/08/11
 */
class MemberCustomRepositoryImpl(
    @Qualifier(value = "memberRedisTemplate") val redisTemplate: RedisTemplate<String, String>,
    private val redisStandaloneProperties: RedisStandaloneProperties,
    val mapper: ObjectMapper
): MemberCustomRepository {

    override fun selectById(id: String): String? =
        redisTemplate.opsForValue().get(id)

    override fun saveMember(member: Member) {
        redisTemplate.opsForValue().set(
            member.id!!,
            mapper.writeValueAsString(member),
            redisStandaloneProperties.expire,
            TimeUnit.MINUTES
        )
    }

    override fun deleteMember(id: String): Boolean =
        redisTemplate.delete(id)


}