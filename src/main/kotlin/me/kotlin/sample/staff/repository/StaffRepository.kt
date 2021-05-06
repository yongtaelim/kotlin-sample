package me.kotlin.sample.staff.repository

import me.kotlin.sample.staff.domain.entity.Staff
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by LYT to 2021/05/01
 */
interface StaffRepository: JpaRepository<Staff, Long>, StaffCustomRepository {
}