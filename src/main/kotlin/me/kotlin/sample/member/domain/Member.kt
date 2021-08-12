package me.kotlin.sample.member.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

/**
 * Created by LYT to 2021/08/11
 */
@RedisHash(value = "member")
class Member(

    @Id
    var id: Long? = null,
    var name: String? = null,
    var age: Int? = null
)