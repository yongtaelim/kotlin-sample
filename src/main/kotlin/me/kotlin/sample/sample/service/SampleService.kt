package me.kotlin.sample.sample.service

import me.kotlin.sample.common.response.CommonResponse
import me.kotlin.sample.member.domain.Member
import me.kotlin.sample.member.repository.MemberRepository
import me.kotlin.sample.staff.domain.entity.Staff
import me.kotlin.sample.staff.repository.StaffRepository
import me.kotlin.sample.store.domain.entity.Store
import me.kotlin.sample.store.repository.StoreRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

/**
 * Created by LYT to 2021/08/06
 */
@Service
class SampleService(
    val staffRepository: StaffRepository,
    val storeRepository: StoreRepository,
    val memberRepository: MemberRepository
) {
    fun getStoreById(id: Long): CommonResponse<Store> {
        val store = storeRepository.findByIdOrNull(id)
            ?: return CommonResponse(HttpStatus.NOT_FOUND)

        return CommonResponse(store)
    }


    fun getStaffById(id: Long): CommonResponse<Staff> {
        val staff = staffRepository.findByIdOrNull(id)
            ?: return CommonResponse(HttpStatus.NOT_FOUND)

        return CommonResponse(staff)
    }

    @Cacheable(value = ["member"], key="#id", cacheManager = "memberManager")
    fun getMemberById(id: String): CommonResponse<String> {
        val member = memberRepository.selectById(id)
            ?: return CommonResponse(HttpStatus.NOT_FOUND)

        return CommonResponse(member)
    }

    fun saveMember(member: Member): CommonResponse<Unit> {
        memberRepository.saveMember(member)
        return CommonResponse(HttpStatus.CREATED)
    }

    fun getMemberByIdFromRepository(id: String): CommonResponse<Member> {
        val member = memberRepository.findByIdOrNull(id)
            ?: return CommonResponse(HttpStatus.NOT_FOUND)

        return CommonResponse(member)
    }

    fun saveMemberFromRepository(member: Member): CommonResponse<Member> {
        return CommonResponse(HttpStatus.CREATED, memberRepository.save(member))
    }

}