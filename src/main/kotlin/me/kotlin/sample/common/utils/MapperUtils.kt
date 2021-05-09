package me.kotlin.sample.common.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/**
 * Created by LYT to 2021/05/06
 */
class MapperUtils {

    companion object {
        private val objectMapper: ObjectMapper = ObjectMapper()
        private val snakeObjectMapper: ObjectMapper = ObjectMapper()

        // data class 구조 deserialize 가능하도록 설정값 추가
        fun getMapper(): ObjectMapper = getObjectMapper(objectMapper)

        fun getSnakeCaseMapper(): ObjectMapper {
            snakeObjectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            return getObjectMapper(snakeObjectMapper)
        }

        private fun getObjectMapper(objectMapper: ObjectMapper) =
            objectMapper
//                .registerKotlinModule()
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
}