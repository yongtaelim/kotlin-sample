package me.kotlin.sample.common.utils

import me.kotlin.sample.sample.FullData
import me.kotlin.sample.sample.SimpleData
import org.junit.jupiter.api.Test

/**
 * Created by LYT to 2021/05/06
 */
class MapperTest {

    @Test
    fun `Mapper Convert Test`() {
        // Given
        val simpleDate = SimpleData(
            lastName = "lim",
            firstName = "yongtae"
        )

        val fullData = MapperUtils.getMapper().convertValue(simpleDate, FullData::class.java)

        // When


        // Then
    }

}