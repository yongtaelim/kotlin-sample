package me.kotlin.sample.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * Created by LYT to 2021/05/15
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "sample")
data class SampleProperties (
    val kotlin: KotlinProperties,
    val java: JavaProperties
) {
    data class KotlinProperties(
        val language: String,
        val studyDays: Int
    )

    data class JavaProperties(
        val language: String,
        val studyDays: Int,
        val test: String
    )
}