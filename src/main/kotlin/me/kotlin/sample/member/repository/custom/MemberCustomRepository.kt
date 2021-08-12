package me.kotlin.sample.member.repository.custom

import me.kotlin.sample.member.domain.Member

/**
 * Created by LYT to 2021/08/11
 */
interface MemberCustomRepository {
    fun selectById(id: Long): Member?
    fun saveMember(member: Member)
    fun deleteMember(id: Long): Boolean
}