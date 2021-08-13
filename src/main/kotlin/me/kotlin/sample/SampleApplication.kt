package me.kotlin.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableCaching
class SampleApplication

fun main(args: Array<String>) {
    runApplication<SampleApplication>(*args)
}
