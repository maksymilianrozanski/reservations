package io.github.maksymilianrozanski.demo.rest

import io.github.maksymilianrozanski.demo.entity.Reservations
import io.github.maksymilianrozanski.demo.service.NotFoundException
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
import org.springframework.http.HttpStatus
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
        val uri = "/api/reservations"
        val mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(200, status)
        val content = mvcResult.response.contentAsString
        val obtainedList = super.mapFromJson(content, Array<Reservations>::class.java)
        Assert.assertEquals(mockedReservation, obtainedList[0])
        Assert.assertEquals(1, obtainedList.size)
    }

    @Test
    fun findAllTestEmptyList() {
        Mockito.`when`(testTableServiceMock.findAll()).thenReturn(listOf())
        val uri = "/api/reservations"
        val mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(200, status)
        val content = mvcResult.response.contentAsString
        val obtainedList = super.mapFromJson(content, Array<Reservations>::class.java)
        Assert.assertEquals(0, obtainedList.size)
    }

    @Test
    fun addNewReservationTest() {
        val uri = "/api/reservations"
        val input = Reservations(title = "title", description = "description",
                start = Timestamp(1561122072971), end = Timestamp(1561122085630))
        val output = Reservations(id = 15, title = input.title,
                description = input.description, start = input.start, end = input.end)
        Mockito.`when`(testTableServiceMock.addReservation(input.title, input.description, input.start, input.end))
                .thenReturn(output)
        val inputJson = super.mapToJson(input)
        val mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(201, status)
        val content = super.mapFromJson(mvcResult.response.contentAsString, Reservations::class.java)
        Assert.assertEquals(output, content)
    }

    @Test
    fun deleteReservationSuccess() {
        val uri = "/api/reservations/10"
        val mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(200, status)
    }

    @Test
    fun deleteReservationNotFound() {
        val uri = "/api/reservations/10"
        Mockito.`when`(testTableServiceMock.deleteReservation(10)).thenAnswer { throw NotFoundException("Not found record with id: 10") }
        val mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), status)
    }
}