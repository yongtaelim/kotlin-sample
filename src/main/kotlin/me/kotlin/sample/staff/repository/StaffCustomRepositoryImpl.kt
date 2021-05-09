package me.kotlin.sample.staff.repository

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.JPQLQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import me.kotlin.sample.common.repository.QuerydslPageAndSortRepository
import me.kotlin.sample.house.domain.entity.QHouse.house
import me.kotlin.sample.staff.domain.entity.QStaff.staff
import me.kotlin.sample.staff.domain.entity.Staff
import me.kotlin.sample.staff.domain.vo.QStaffVo
import me.kotlin.sample.staff.domain.vo.StaffVo
import me.kotlin.sample.store.domain.entity.QStore.store
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import javax.persistence.EntityManager
import javax.transaction.Transactional

/**
 * Created by LYT to 2021/05/01
 */
open class StaffCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
    private val entityManager: EntityManager
) : StaffCustomRepository, QuerydslPageAndSortRepository(entityManager, Staff::class.java) {

    /**
     * ==================================================
     *               간단한 CRUD 케이스
     * ==================================================
     */

    /**
     * 전체 조회
     */
    override fun searchAll(): List<Staff> =
        queryFactory
            .selectFrom(staff)
            .fetch()

    /**
     * VO 객체에 QueryProjection Annotation을 사용하여 return 객체를 설정한 케이스
     */
    override fun search(name: String): StaffVo? =
        queryFactory
            .select(
                QStaffVo(
                    staff.id,
                    staff.name
                )
            )
            .from(staff)
            .where(staff.name.eq(name))
            .fetchOne()


    /**
     * Projections.fields를 사용하여 return 객체를 설정한 케이스
     */
    fun search2(name: String): StaffVo? =
        queryFactory
            .select(
                Projections.fields(
                    StaffVo::class.java,
                    staff.id,
                    staff.name
                )
            )
            .from(staff)
            .where(staff.name.eq(name))
            .fetchOne()


    /**
     * Default Entity로 return 받는 케이스
     */
    override fun findStaffById(id: Long): Staff? =
        queryFactory
            .selectFrom(staff)
            .where(staff.id.eq(id))
            .fetchOne()


    /**
     * delete
     *   - Transactional Annotation require!!
     */
    @Transactional
    override fun deleteQuery(name: String): Long? =
        queryFactory.delete(staff)
            .where(staff.name.eq(name))
            .execute()


    /**
     * update
     *   - Transactional Annotation require!!
     */
    @Transactional
    override fun updateQuery(oldName: String, newName: String): Long? =
        queryFactory.update(staff)
            .set(staff.name, newName)
            .where(staff.name.eq(oldName))
            .execute()


    /**
     * ==================================================
     *               게시판 관련 케이스
     * ==================================================
     */

    /**
     * Paging 기능 추가 케이스
     *   - QuerydslRepositorySupport를 상속받아 사용하는 경우엔 getQuerydsl로 사용할 수 있지만 현재 구성으로는 getQuerydsl을 사용할 수 없음.
     *   - 방안 : 상속받은 QuerydslPageAndSortRepository 클래스에서 QuerydslRepositorySupport 내부 getQuerydsl 메서드를 구현하여 활용
     *
     *   - 게시판 Paging, Order에 사용
     */
    override fun findStaffsByPageImpl(pageable: Pageable): PageImpl<StaffVo> {
        val query: JPQLQuery<StaffVo> = queryFactory
            .select(
                QStaffVo(staff.id, staff.name)
            )
            .from(staff)

        return super.getPageImpl(pageable, query)
    }

    /**
     * Page && Sort 기능과 검색조건 포함된 케이스
     */
    override fun findDifficultByPageImpl(age: Int, name: String?, lastName: String?, pageable: Pageable): PageImpl<StaffVo> {
        val query: JPQLQuery<StaffVo> = queryFactory
            .select(
                QStaffVo(staff.id, staff.name)
            )
            .from(staff)
            .where(
                staff.age.eq(age),
                eqLastName(lastName),
                eqName(name)
            )


        return super.getPageImpl(pageable, query)
    }

    /**
     * Dynamic Query에 사용될 private mothod
     */
    private fun eqLastName(lastName: String?): BooleanExpression? =
        if (lastName != null)
            staff.lastName.eq(lastName)
        else
            null


    /**
     * Dynamic Query에 사용될 private mothod
     */
    private fun eqName(name: String?): BooleanExpression? =
        if (name != null)
            staff.name.eq(name)
        else
            null


    /**
     * Dynamic Query 케이스
     *   - join, where절에 null을 사용하여 dynamic하게 JOQL 생성 가능
     *   - querydsl에 ? null을 사용하기 보단 private method로 빼서 가독성 상승
     *
     *   - 게시판 검색 용도로 사용
     */
    override fun dynamicQuery(name: String): Staff? =
        queryFactory
            .selectFrom(staff)
            .join(store).on(eqName(name))
            .where(eqName(name))
            .fetchOne()

    /**
     * Many Dynamic Query 케이스
     *   - 단 건의 dynamic query 작성 시 메서드로 빼는 것이 가독성이 좋으나 많아진다면 한번에 처리하는 메서드로 처리한다.
     * @param name String?
     * @param address String?
     * @return Staff?
     */
    override fun manyDynamicQuery(name: String?, address: String?): Staff? =
        queryFactory
            .selectFrom(staff)
            .where(searchCondition(name = name, address = address))
            .fetchOne()


    private fun searchCondition(name: String?, address: String?): BooleanBuilder {
        val builder = BooleanBuilder()

        name?.let { builder.and(staff.name.eq(it)) }
        address?.let { builder.and(staff.name.eq(it)) }

        return builder
    }


    /**
     * ==================================================
     *               대용량 데이터 처리 케이스
     * ==================================================
     */

    /**
     * exists 사용
     *   - 추천하지 않음 데이터 양이 늘어날수록 성능이 낮아짐
     */
    override fun findExist(name: String): Boolean? {
        val exists = queryFactory
            .selectOne()
            .from(staff)
            .where(staff.name.eq(name))
            .fetchAll()
            .exists()

        return queryFactory
            .select(exists)
            .from(staff)
            .fetchOne()
    }

    /**
     * exists 성능 이슈 대안
     *   - 대용량 처리 시 성능 향상
     */
    override fun findLimitOneInsteadOfExist(name: String): Boolean {
        val fetchFirst = queryFactory
            .selectOne()
            .from(staff)
            .where(staff.name.eq(name))
            .fetchFirst() // limit(1).fetchOne()

        return fetchFirst != null // 값이 없으면 0이 아니라 null 반환
    }

    /**
     * 커버링 인덱스 케이스
     *   - 대용량 처리 시 성능 향상
     */
    override fun findByCoveringIndex(name: String): List<Staff> {
        val houseIds: List<Long> = queryFactory
            .select(house.id)
            .from(house)
            .where(house.name.eq(name))
            .fetch()

        return if (houseIds.isEmpty()) {
            arrayListOf()
        } else queryFactory
            .selectFrom(staff)
            .join(staff.house, house)
            .where(house.id.`in`(houseIds))
            .fetch()
    }

}