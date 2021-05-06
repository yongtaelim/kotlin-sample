package me.kotlin.sample.common.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * Created by LYT to 2021/05/01
 */
@Configuration
class QuerydslConfig (@PersistenceContext val entityManager: EntityManager) {
    @Bean
    fun jpqQueryFactory() = JPAQueryFactory(entityManager)
}