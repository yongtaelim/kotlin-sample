package me.kotlin.sample.staff.repository

import me.kotlin.sample.staff.domain.entity.Staff
import me.kotlin.sample.staff.domain.vo.StaffGroupByVO
import me.kotlin.sample.staff.domain.vo.StaffVo
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

/**
 * Created by LYT to 2021/05/01
 */
interface StaffCustomRepository {
    fun searchAll(): List<Staff?>?

    fun search(name: String): StaffVo?

    fun findStaffById(id: Long): Staff?

    fun selectGroupById(pageable: Pageable): PageImpl<StaffGroupByVO>

    fun deleteQuery(name: String): Long?

    fun updateQuery(oldName: String, newName: String): Long?

    fun findStaffsByPageImpl(pageable: Pageable): PageImpl<StaffVo>

    fun findDifficultByPageImpl(age: Int, name: String?, lastName: String?, pageable: Pageable): PageImpl<StaffVo>

    fun dynamicQuery(name: String): Staff?

    fun manyDynamicQuery(name: String?, address: String?): Staff?

    fun findExist(name: String): Boolean?

    fun findLimitOneInsteadOfExist(name: String): Boolean?

    fun findByCoveringIndex(name: String): List<Staff?>?
}