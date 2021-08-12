package me.kotlin.sample.common.utils

import me.kotlin.sample.sample.domain.OtherData
import me.kotlin.sample.sample.domain.SimpleData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Created by LYT to 2021/05/06
 */
class MapperTest {

    @Test
    fun `Mapper Convert Test`() {
        // Given
        val otherData = OtherData(
            lastName = "lim",
            firstName = "yongtae",
            address = "강남",
            age = 33
        )


        // When
        val simpleData = MapperUtils.getMapper().convertValue(otherData, SimpleData::class.java)

        // Then
        assertThat(simpleData.lastName).isEqualTo("lim")
    }

}