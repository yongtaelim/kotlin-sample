package me.kotlin.sample.store.repository

import me.kotlin.sample.store.domain.entity.Store
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by LYT to 2021/05/01
 */
interface StoreRepository: JpaRepository<Store, Long> {
}