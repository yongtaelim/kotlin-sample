package me.kotlin.sample.controller

import me.kotlin.sample.common.config.BaseMvcTest
import me.kotlin.sample.staff.domain.entity.Staff
import me.kotlin.sample.staff.repository.StaffRepository
import me.kotlin.sample.store.domain.entity.Store
import me.kotlin.sample.store.repository.StoreRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

/**
 * Created by LYT to 2021/08/06
 */
@Transactional
@DisplayName("샘플 테스트")
internal class SampleController2Test(
    val staffRepository: StaffRepository,
    val storeRepository: StoreRepository
): BaseMvcTest() {

    companion object {
        const val API_URL = "/sample"
    }

    @Nested
    @DisplayName("스토어 테스트")
    inner class StoreTest {
        lateinit var store: Store

        @BeforeEach
        fun before() {
            applyEncodingFilter()

            store = storeRepository.save(
                Store(
                    name = "둘리회사",
                    address = "성수동 12-2"
                )
            )
        }

        @Test
        fun `스토어 조회`() {
            // Given


            // When, Then
            mockMvc.perform(
                get("$API_URL/store/{id}", store.id)
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").value(HttpStatus.OK.reasonPhrase))
                .andExpect(jsonPath("content.id").value(store.id))
                .andExpect(jsonPath("content.name").value(store.name))
                .andExpect(jsonPath("content.address").value(store.address))
        }

        @Test
        fun `스토어 조회 - 데이터 못찾는 경우`() {
            // Given


            // When, Then
            mockMvc.perform(
                get("$API_URL/store/{id}", "192844")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("message").value(HttpStatus.NOT_FOUND.reasonPhrase))
                .andExpect(jsonPath("content").isEmpty)
        }

    }

    @Nested
    @DisplayName("스테프 테스트")
    inner class StaffTest {
        lateinit var staff: Staff

        @BeforeEach
        fun before() {
            staff = staffRepository.save(
                Staff(
                    name = "둘리",
                    age = 31,
                    lastName = "김"
                )
            )
        }

        @Test
        fun `스태프 조회`() {
            // Given


            // When, Then
            mockMvc.perform(
                get("$API_URL/staff/{id}", staff.id)
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("message").value(HttpStatus.OK.reasonPhrase))
                .andExpect(jsonPath("content.id").value(staff.id))
                .andExpect(jsonPath("content.name").value(staff.name))
                .andExpect(jsonPath("content.age").value(staff.age))
                .andExpect(jsonPath("content.lastName").value(staff.lastName))

        }

        @Test
        fun `스태프 조회 - 데이터 못찾는 경우`() {
            // Given


            // When, Then
            mockMvc.perform(
                get("$API_URL/staff/{id}", "4266523146")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("message").value(HttpStatus.NOT_FOUND.reasonPhrase))
                .andExpect(jsonPath("content").isEmpty)
        }
    }

}