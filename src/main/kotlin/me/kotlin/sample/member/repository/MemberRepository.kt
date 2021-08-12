package me.kotlin.sample.member.repository

import me.kotlin.sample.member.domain.Member
import me.kotlin.sample.member.repository.custom.MemberCustomRepository
import org.springframework.data.repository.CrudRepository

/**
 * Created by LYT to 2021/08/11
 */
interface MemberRepository: CrudRepository<Member, Long>, MemberCustomRepository {
}