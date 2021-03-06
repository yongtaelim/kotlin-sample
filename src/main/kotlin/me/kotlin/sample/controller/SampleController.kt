package me.kotlin.sample.controller

import me.kotlin.sample.common.properties.SampleProperties
import me.kotlin.sample.member.domain.Member
import me.kotlin.sample.sample.service.SampleService
import org.springframework.web.bind.annotation.*

/**
 * Created by LYT to 2021/05/15
 */
@RestController
@RequestMapping("/sample")
class SampleController(
    val sampleProperties: SampleProperties,
    val service: SampleService
) {

    @GetMapping
    fun getSetting() = sampleProperties.kotlin.language

    @GetMapping("store/{id}")
    fun getStoreById(
        @PathVariable("id") id: Long
    ) = service.getStoreById(id)

    @GetMapping("/staff/{id}")
    fun getStaffById(
        @PathVariable("id") id: Long
    ) = service.getStaffById(id)

    @GetMapping("/member/{id}")
    fun getMemberById(
        @PathVariable("id") id: String
    ) = service.getMemberById(id)

    @PostMapping("/member")
    fun saveMember(
        @RequestBody member: Member
    ) = service.saveMember(member)

    @GetMapping("/member/repo/{id}")
    fun getMemberByIdFromRepository(
        @PathVariable("id") id: String
    ) = service.getMemberByIdFromRepository(id)

    @PostMapping("/member/repo")
    fun saveMemberFromRepository(
        @RequestBody member: Member
    ) = service.saveMemberFromRepository(member)
}