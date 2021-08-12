package me.kotlin.sample.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

/**
 * Created by LYT to 2021/04/06
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BaseMvcTest {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var ctx: WebApplicationContext

    protected fun applyEncodingFilter() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx)
            .addFilters<DefaultMockMvcBuilder>(
                CharacterEncodingFilter("UTF-8", true),
            )
            .alwaysDo<DefaultMockMvcBuilder>(print())
            .build()
    }
}
