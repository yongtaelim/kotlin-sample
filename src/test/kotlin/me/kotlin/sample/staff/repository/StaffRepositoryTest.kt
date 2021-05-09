package me.kotlin.sample.staff.repository

import me.kotlin.sample.common.config.TestConfig
import me.kotlin.sample.staff.domain.entity.Staff
import me.kotlin.sample.store.domain.entity.Store
import me.kotlin.sample.store.repository.StoreRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.TestConstructor

/**
 * Created by LYT to 2021/04/01
 */
@DataJpaTest
@Import(TestConfig::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class StaffRepositoryTest(
    val staffRepository: StaffRepository,
    val storeRepository: StoreRepository
) {

    @BeforeEach
    fun beforeEach() {
        storeRepository.deleteAll()
        staffRepository.deleteAll()

        val staffs = listOf(
            Staff(name = "yong1", age = 20, lastName = "lim1"),
            Staff(name = "yong11", age = 20, lastName = "lim11"),
            Staff(name = "yong21", age = 20, lastName = "lim21"),
            Staff(name = "yong31", age = 20, lastName = "lim31"),
            Staff(name = "yong41", age = 20, lastName = "lim41"),
            Staff(name = "yong51", age = 20, lastName = "lim51"),
            Staff(name = "yong61", age = 20, lastName = "lim61"),
            Staff(name = "yong71", age = 20, lastName = "lim71"),
            Staff(name = "yong81", age = 20, lastName = "lim721"),
            Staff(name = "yong91", age = 20, lastName = "lim731"),
            Staff(name = "yong101", age = 20, lastName = "lim741"),
            Staff(name = "yong171", age = 20, lastName = "lim761"),
            Staff(name = "yong1271", age = 20, lastName = "lim7261"),
            Staff(name = "yong282", age = 21, lastName = "lim862"),
            Staff(name = "yong3", age = 22, lastName = "lim3"),
            Staff(name = "yong4", age = 23, lastName = "lim4"),
            Staff(name = "yong5", age = 24, lastName = "lim5"),
            Staff(name = "yong6", age = 25, lastName = "lim6"),
            Staff(name = "yong7", age = 26, lastName = "lim7"),
            Staff(name = "yong8", age = 27, lastName = "lim8"),
            Staff(name = "yong9", age = 28, lastName = "lim9"),
            Staff(name = "yong10", age = 29, lastName = "lim10")
        )

        val store = Store(name = "fastview", address = "강남역 미왕빌딩")
        store.addStaffs(staffs = staffs)
        storeRepository.save(store)
    }

    @Test
    @DisplayName("전체 조회 테스트")
    fun searchAllTest() {
        // Given


        // When
        val searchAll = staffRepository.searchAll()

        // Then
        assertThat(searchAll).hasSize(22)
    }

    @Test
    @DisplayName("조건 조회 테스트")
    fun searchTest() {
        // Given
        val name = "yong1"

        // When
        val staff = staffRepository.search(name)

        // Then
        assertThat(staff).isNotNull
        assertThat(staff?.name).isEqualTo(name)
    }

    @Test
    @DisplayName("삭제 테스트")
    fun deleteQueryTest() {
        // Given
        val name = "yong1"

        // When
        val staffId = staffRepository.deleteQuery(name)

        // Then
        assertThat(staffId).isNotNull
    }

    @Test
    @DisplayName("수정 테스트")
    fun updateQueryTest() {
        // Given
        val id = 1L
        val oldName = "yong1"
        val newName = "임용용"

        // When
        staffRepository.updateQuery(oldName, newName)

        // Then
        val staff = staffRepository.search(newName)
        assertThat(staff).isNotNull
        assertThat(staff?.name).isEqualTo(newName)
    }

    @Test
    @DisplayName("staffs 조회 시 page && sort 적용 테스트")
    fun findStaffsByPageImplTest() {
        // Given
        val page = 0
        val pageSize = 10
        val order = Sort.Order.desc("id")
        val pageable = generatePageable(page = page, pageSize = pageSize, order)

        // When
        val staffsPageImpl = staffRepository.findStaffsByPageImpl(pageable)

        // Then
        assertThat(staffsPageImpl.number).isEqualTo(page)
        assertThat(staffsPageImpl.totalPages).isEqualTo(3)
        assertThat(staffsPageImpl.content).hasSize(10)
    }



    @Test
    @DisplayName("staffs 조회 시 검색 조건 및 page && sort 적용 테스트")
    fun findDifficultByPageImplTest() {
        // Given
        val page = 1
        val pageSize = 10
        val order1 = Sort.Order.desc("id")
        val order2 = Sort.Order.desc("name")
        val order3 = Sort.Order.asc("lastName")

        val pageable = generatePageable(page = page, pageSize = pageSize, order1, order2, order3)

        // When
        val staffsPageImpl = staffRepository.findDifficultByPageImpl(20, null, null, pageable)

        // Then
        for (staffVo in staffsPageImpl) {
            println("response data: ${staffVo.toString()}")
        }

        assertThat(staffsPageImpl.number).isEqualTo(page)
        assertThat(staffsPageImpl.totalPages).isEqualTo(2)
        assertThat(staffsPageImpl.content).hasSize(3)
    }

    private fun generatePageable(page: Int, pageSize: Int, vararg order: Sort.Order): PageRequest =
        PageRequest.of(page, pageSize, Sort.by(order.asList()))
}
