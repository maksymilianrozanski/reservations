package io.github.maksymilianrozanski.demo.rest

import io.github.maksymilianrozanski.demo.entity.Reservations
import io.github.maksymilianrozanski.demo.service.TestTableService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.sql.Timestamp

@Profile("test")
@Configuration
class TestTableServiceTestConfiguration {

    @Bean
    @Primary
    fun testTableService(): TestTableService {
        println("Inside testTableService test bean")
        return Mockito.mock(TestTableService::class.java)
    }
}

@ActiveProfiles("test")
class MyRestControllerTest : AbstractTest() {

    @Autowired
    private lateinit var testTableServiceMock: TestTableService

    @Before
    override fun setup() {
        super.setup()
    }

    @Test
    fun findAllTest() {
        val mockedReservation = Reservations(id = 20, title = "This is title returned by mock service",
                description = "description", start = Timestamp(1561117396873),
                end = Timestamp(1561117410153), user = "Some user")
        Mockito.`when`(testTableServiceMock.findAll())
                .thenReturn(listOf(mockedReservation))
        val uri = "/api/findAll"
        val mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(200, status)
        val content = mvcResult.response.contentAsString
        val obtainedList = super.mapFromJson(content, Array<Reservations>::class.java)
        Assert.assertEquals(mockedReservation, obtainedList[0])
        Assert.assertEquals(1, obtainedList.size)
    }
}