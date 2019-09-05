package io.github.maksymilianrozanski.demo.rest

import io.github.maksymilianrozanski.demo.entity.Reservations
import io.github.maksymilianrozanski.demo.entity.User
import io.github.maksymilianrozanski.demo.security.CustomUserDetailsService
import io.github.maksymilianrozanski.demo.service.AlreadyBookedException
import io.github.maksymilianrozanski.demo.service.NotFoundException
import io.github.maksymilianrozanski.demo.service.ReservationsService
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.reset
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
    fun reservationsService(): ReservationsService {
        return Mockito.mock(ReservationsService::class.java)
    }

    @Bean
    @Primary
    fun userDetailsService(): CustomUserDetailsService {
        return Mockito.mock(CustomUserDetailsService::class.java)
    }
}

@ActiveProfiles("test")
class MyRestControllerTest : AbstractTest() {

    @Autowired
    private lateinit var reservationsServiceMock: ReservationsService

    @Autowired
    private lateinit var userDetailsServiceMock: CustomUserDetailsService

    @Before
    override fun setup() {
        super.setup()
    }

    @After
    fun after() {
        reset(reservationsServiceMock, userDetailsServiceMock)
    }

    @Test
    fun getUserRolesTest() {
        Mockito.`when`(userDetailsServiceMock.currentUserRoles()).thenReturn(listOf("ADMIN", "USER", "GUEST"))
        val uri = "/api/roles"
        val mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(200, status)
        val content = mvcResult.response.contentAsString
        val obtainedRolesArray = super.mapFromJson(content, Array<String>::class.java)
        Assert.assertEquals(listOf("ADMIN", "USER", "GUEST"), obtainedRolesArray.toList())
    }

    @Test
    fun findAllByAdminTest() {
        val mockedReservation = Reservations(reservationId = 20, title = "This is title returned by mock service",
                description = "description", start = Timestamp(1561117300000),
                end = Timestamp(1561117400000), user = null)
        val mockedReservation2 = Reservations(reservationId = 21, title = "This is second item's title",
                description = "description", start = Timestamp(1567609000000),
                end = Timestamp(1567610500000), user = User("firstName", "lastName", "username", "email@example.com"))
        Mockito.`when`(reservationsServiceMock.findAll())
                .thenReturn(listOf(mockedReservation, mockedReservation2))
        Mockito.`when`(userDetailsServiceMock.currentUserRoles()).thenReturn(listOf("Role(roleName=USER)", "Role(roleName=ADMIN)"))
        val uri = "/api/reservations"
        val mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(200, status)
        val content = mvcResult.response.contentAsString
        val obtainedList = super.mapFromJson(content, Array<Reservations>::class.java)
        Assert.assertEquals(listOf(mockedReservation, mockedReservation2), obtainedList.toList())
    }

    @Test
    fun findAllByUserTest() {
        val mockedReservation = Reservations(reservationId = 20, title = "This is title returned by mock service",
                description = "description", start = Timestamp(1561117300000),
                end = Timestamp(1561117400000), user = null)
        Mockito.`when`(reservationsServiceMock.findUnoccupiedReservations()).thenReturn(listOf(mockedReservation))
        Mockito.`when`(userDetailsServiceMock.currentUserRoles()).thenReturn(listOf("Role(roleName=USER)"))
        val uri = "/api/reservations"
        val mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(200, status)
        val content = mvcResult.response.contentAsString
        val obtainedList = super.mapFromJson(content, Array<Reservations>::class.java)
        Assert.assertEquals(listOf(mockedReservation), obtainedList.toList())
    }

    @Test
    fun findAllEmptyListByAdminTest() {
        Mockito.`when`(userDetailsServiceMock.currentUserRoles()).thenReturn(listOf("Role(roleName=USER)", "Role(roleName=ADMIN)"))
        Mockito.`when`(reservationsServiceMock.findAll()).thenReturn(listOf())
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
    fun findAllEmptyListByUserTest() {
        Mockito.`when`(userDetailsServiceMock.currentUserRoles()).thenReturn(listOf("Role(roleName=USER)"))
        Mockito.`when`(reservationsServiceMock.findUnoccupiedReservations()).thenReturn(listOf())
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
                start = Timestamp(1561122000000), end = Timestamp(1561122000000))
        val output = Reservations(reservationId = 15, title = input.title,
                description = input.description, start = input.start, end = input.end)
        Mockito.`when`(reservationsServiceMock.addReservation(input.title, input.description, input.start, input.end))
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
        Mockito.`when`(reservationsServiceMock.deleteReservation(10)).thenAnswer { throw NotFoundException("Not found record with id: 10") }
        val mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), status)
    }

    @Test
    fun bookingReservationSuccess() {
        val userMock = Mockito.mock(User::class.java)
        Mockito.`when`(userDetailsServiceMock.currentUser()).thenReturn(userMock)
        Mockito.`when`(userMock.username).thenReturn("SomeUserName")
        Mockito.`when`(userDetailsServiceMock.currentUserRoles()).thenReturn(listOf("Role(roleName=USER)"))
        val reservation = Reservations(reservationId = 15, user = null, title = "title", description = "description",
                start = Timestamp(1561122000000), end = Timestamp(1561122000000))
        Mockito.`when`(reservationsServiceMock.findById(15)).thenReturn(reservation)
        val uri = "/api/reservations"
        val serviceResponse = Reservations(reservationId = 15, user = User(firstName = "First name", lastName = "Last name",
                email = "example@example.com", username = "SomeUserName"),
                title = "title", description = "description",
                start = Timestamp(1561122000000), end = Timestamp(1561122000000))
        val inputJson = super.mapToJson(reservation)
        Mockito.`when`(reservationsServiceMock.addUserToNotReservedReservation(userMock, 15))
                .thenReturn(serviceResponse)
        val mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(200, status)
        val content = super.mapFromJson(mvcResult.response.contentAsString, Reservations::class.java)
        Assert.assertEquals(serviceResponse, content)
    }

    @Test
    fun bookingReservationNotFoundException() {
        val userMock = Mockito.mock(User::class.java)
        Mockito.`when`(userDetailsServiceMock.currentUser()).thenReturn(userMock)
        Mockito.`when`(userMock.username).thenReturn("SomeUserName")
        Mockito.`when`(userDetailsServiceMock.currentUserRoles()).thenReturn(listOf("Role(roleName=USER)"))
        val uri = "/api/reservations"
        val reservation = Reservations(reservationId = 15, user = User(firstName = "First name", lastName = "Last name",
                email = "example@example.com", username = "SomeUserName"), title = "title", description = "description",
                start = Timestamp(1561122072971), end = Timestamp(1561122085630))
        val inputJson = super.mapToJson(reservation)
        Mockito.`when`(reservationsServiceMock.addUserToNotReservedReservation(userMock, 15))
                .thenAnswer { throw NotFoundException("Not found record with id: 15") }
        val mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), status)
    }

    @Test
    fun bookingReservationAlreadyBooked() {
        val userMock = Mockito.mock(User::class.java)
        Mockito.`when`(userDetailsServiceMock.currentUser()).thenReturn(userMock)
        Mockito.`when`(userDetailsServiceMock.currentUser()).thenReturn(userMock)
        Mockito.`when`(userMock.username).thenReturn("SomeUserName")
        Mockito.`when`(userDetailsServiceMock.currentUserRoles()).thenReturn(listOf("Role(roleName=USER)"))
        val uri = "/api/reservations"
        val reservation = Reservations(reservationId = 15, user = User(firstName = "First name", lastName = "Last name",
                email = "example@example.com", username = "SomeUserName"), title = "title", description = "description",
                start = Timestamp(1561122072971), end = Timestamp(1561122085630))
        val inputJson = super.mapToJson(reservation)
        Mockito.`when`(reservationsServiceMock.addUserToNotReservedReservation(userMock, 15))
                .thenAnswer { throw AlreadyBookedException("Reservation with id: 15 is already booked") }
        val mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(HttpStatus.CONFLICT.value(), status)
    }

    @Test
    fun bookingReservationByTheSameUserAgain() {
        val userMock = Mockito.mock(User::class.java)
        Mockito.`when`(userDetailsServiceMock.currentUser()).thenReturn(userMock)
        Mockito.`when`(userDetailsServiceMock.currentUser()).thenReturn(userMock)
        Mockito.`when`(userMock.username).thenReturn("SomeUserName")
        Mockito.`when`(userDetailsServiceMock.currentUserRoles()).thenReturn(listOf("Role(roleName=USER)"))
        val reservationMock = Mockito.mock(Reservations::class.java)
        Mockito.`when`(reservationsServiceMock.findById(15)).thenReturn(reservationMock)
        Mockito.`when`(reservationMock.user).thenReturn(userMock)
        val uri = "/api/reservations"
        val reservation = Reservations(reservationId = 15, user = User(firstName = "First name", lastName = "Last name",
                email = "example@example.com", username = "SomeUserName"), title = "title", description = "description",
                start = Timestamp(1561122072971), end = Timestamp(1561122085630))
        val inputJson = super.mapToJson(reservation)
        val mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn()
        val status = mvcResult.response.status
        Assert.assertEquals(HttpStatus.NO_CONTENT.value(), status)
    }
}
