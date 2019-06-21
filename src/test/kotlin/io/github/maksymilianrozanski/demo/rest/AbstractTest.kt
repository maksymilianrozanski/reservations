package io.github.maksymilianrozanski.demo.rest

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.maksymilianrozanski.demo.KotlinSpringApplication
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [KotlinSpringApplication::class])
@WebAppConfiguration
abstract class AbstractTest {

    lateinit var mvc: MockMvc

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    fun mapToJson(obj: Any): String = ObjectMapper().writeValueAsString(obj)

    fun <T> mapFromJson(json: String, clazz: Class<T>): T = ObjectMapper().readValue(json, clazz)
}