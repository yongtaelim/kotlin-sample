package me.kotlin.sample.staff.domain.vo

import com.querydsl.core.annotations.QueryProjection

/**
 * Created by LYT to 2021/05/01
 */
class StaffVo @QueryProjection constructor(
    val id: Long,
    val name: String
) {
    override fun toString(): String {
        return "StaffVo(id=$id, name='$name')"
    }
}