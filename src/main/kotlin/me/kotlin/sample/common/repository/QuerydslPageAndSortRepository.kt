package me.kotlin.sample.common.repository

import com.querydsl.core.types.dsl.PathBuilderFactory
import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.Querydsl
import javax.persistence.EntityManager

/**
 * Created by LYT to 2021/04/01
 * QuerydslRepositorySupport 참고하여 생성
 */
open class QuerydslPageAndSortRepository(
    private val entityManager: EntityManager,
    private val clazz: Class<*>
) {

    private fun getQuerydsl(): Querydsl {
        val builder = PathBuilderFactory().create(clazz)
        return Querydsl(entityManager, builder)
    }

    fun <T> getPageImpl(pageable: Pageable, query: JPQLQuery<T>): PageImpl<T> {
        return if (query.metadata.groupBy.size > 0) {
            getPageImplIfGroupBy(pageable, query)
        } else {
            getPageImplIfNotGroupBy(pageable, query)
        }
    }

    private fun <T> getPageImplIfGroupBy(pageable: Pageable, query: JPQLQuery<T>): PageImpl<T> {
        val queryResult = query.fetch()

        val totalCount = queryResult.size
        val results = queryResult.subList(pageable.pageNumber, pageable.pageSize * (pageable.pageNumber + 1))
        return PageImpl(results, pageable, totalCount.toLong())
    }

    private fun <T> getPageImplIfNotGroupBy(pageable: Pageable, query: JPQLQuery<T>): PageImpl<T> {
        val totalCount = query.fetchCount()

        val results = getQuerydsl()
            .applyPagination(pageable, query)
            .fetch()

        return PageImpl(results, pageable, totalCount)
    }
}