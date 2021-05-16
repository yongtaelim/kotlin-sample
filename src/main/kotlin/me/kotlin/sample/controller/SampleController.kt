package me.kotlin.sample.controller

import me.kotlin.sample.common.properties.SampleProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by LYT to 2021/05/15
 */
@RestController
@RequestMapping("/sample")
class SampleController(
    val sampleProperties: SampleProperties
) {

    @GetMapping
    fun getSetting() = sampleProperties.kotlin.language
}