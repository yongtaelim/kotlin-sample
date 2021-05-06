package me.kotlin.sample.common.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * Created by LYT to 2021/04/01
 */
@TestConfiguration
class TestConfig(@PersistenceContext val entityManager: EntityManager) {
    @Bean
    fun jpbQueryFactory() = JPAQueryFactory(entityManager)
}