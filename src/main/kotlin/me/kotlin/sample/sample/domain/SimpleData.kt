package me.kotlin.sample.sample.domain

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

/**
 * Created by LYT to 2021/05/06
 */
class SimpleData(
    val lastName: String? = null,
    val firstName: String? = null,
//    val etc: String? = null,
)