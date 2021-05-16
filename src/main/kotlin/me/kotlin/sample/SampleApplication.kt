package me.kotlin.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class SampleApplication

fun main(args: Array<String>) {
    runApplication<SampleApplication>(*args)
}
