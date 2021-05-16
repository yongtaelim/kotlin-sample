package me.kotlin.sample.common.properties

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestPropertySource

/**
 * Created by LYT to 2021/05/15
 */
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@EnableConfigurationProperties(SampleProperties::class)
@TestPropertySource("classpath:application.yml")
internal class SamplePropertiesTest(
    val sampleProperties: SampleProperties
) {

    @Test
    fun `properties 조회`() {
        assertThat(sampleProperties.kotlin.language).isEqualTo("kotlin")
        assertThat(sampleProperties.kotlin.studyDays).isEqualTo(30)

        assertThat(sampleProperties.java.language).isEqualTo("java")
        assertThat(sampleProperties.java.studyDays).isEqualTo(90)
        assertThat(sampleProperties.java.test).isEqualTo("javaTest")
    }

}

