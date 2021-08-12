package me.kotlin.sample.member.repository.custom

import me.kotlin.sample.common.redis.properties.RedisStandaloneProperties
import me.kotlin.sample.member.domain.Member
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

/**
 * Created by LYT to 2021/08/11
 */
class MemberCustomRepositoryImpl(
    @Qualifier(value = "memberRedisTemplate") val redisTemplate: RedisTemplate<Long, Member>,
    private val redisStandaloneProperties: RedisStandaloneProperties
): MemberCustomRepository {

    override fun selectById(id: Long): Member? =
        redisTemplate.opsForValue().get(id)

    override fun saveMember(member: Member) {
        redisTemplate.opsForValue().set(
            member.id!!,
            member,
            redisStandaloneProperties.expire,
            TimeUnit.MINUTES
        )
    }

    override fun deleteMember(id: Long): Boolean =
        redisTemplate.delete(id)


}