package me.kotlin.sample.common.utils

import com.fasterxml.jackson.databind.ObjectMapper
import me.kotlin.sample.sample.OtherData
import me.kotlin.sample.sample.SimpleData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

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