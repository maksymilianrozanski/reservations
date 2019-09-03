package io.github.maksymilianrozanski.demo.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.never
import io.github.maksymilianrozanski.demo.dao.ReservationsRepository
import io.github.maksymilianrozanski.demo.entity.Reservations
import io.github.maksymilianrozanski.demo.entity.User
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.sql.Timestamp
import java.util.*
import kotlin.test.assertFailsWith

internal class ReservationsServiceImplTest {

    lateinit var service: ReservationsServiceImpl
    @Mock
    lateinit var repositoryMock: ReservationsRepository

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        service = ReservationsServiceImpl()
        service.repository = repositoryMock
    }

    @Test
    fun findAll() {
        service.findAll()
        Mockito.verify(repositoryMock).findAll()
    }

    @Test
    fun findUnoccupiedReservations() {
        val reservationMock1 = Mockito.mock(Reservations::class.java)
        val reservationMock2 = Mockito.mock(Reservations::class.java)
        val reservationMock3 = Mockito.mock(Reservations::class.java)
        val reservationMock4 = Mockito.mock(Reservations::class.java)
        val userMock1 = Mockito.mock(User::class.java)
        val userMock2 = Mockito.mock(User::class.java)
        Mockito.`when`(reservationMock1.user).thenReturn(null)
        Mockito.`when`(reservationMock2.user).thenReturn(userMock1)
        Mockito.`when`(reservationMock3.user).thenReturn(userMock2)
        Mockito.`when`(reservationMock4.user).thenReturn(null)
        val reservations = listOf(reservationMock1, reservationMock2, reservationMock3, reservationMock4)
        Mockito.`when`(repositoryMock.findAll()).thenReturn(reservations)

        val unoccupiedReservations = service.findUnoccupiedReservations()
        Assert.assertEquals(listOf(reservationMock1, reservationMock4), unoccupiedReservations)
    }

    @Test
    fun findByIdSuccess() {
        val title = "Example title"
        val description = "Example description"
        val start = Timestamp(1561037749627L)
        val end = Timestamp(1561037763719L)
        val toReturnByRepository = Reservations(reservationId = 10, title = title, description = description,
                start = start, end = end)
        Mockito.`when`(repositoryMock.findById(10)).thenReturn(Optional.of(toReturnByRepository))
        val obtainedValue = service.findById(10)
        Assert.assertEquals(toReturnByRepository, obtainedValue)
    }

    @Test(expected = NotFoundException::class)
    fun findByIdNotFound() {
        Mockito.`when`(repositoryMock.findById(10)).thenReturn(Optional.empty())
        service.findById(10)
    }

    @Test
    fun addReservation() {
        val title = "Example title"
        val description = "Example description"
        val start = Timestamp(1561037749627L)
        val end = Timestamp(1561037763719L)
        val toReturnByRepository = Reservations(reservationId = 10, title = title, description = description,
                start = start, end = end)
        Mockito.`when`(repositoryMock.save(argThat<Reservations> {
            this.title == title && this.description == description
                    && this.start == start && this.end == end
        })).thenReturn(toReturnByRepository)
        val returnedFromService = service.addReservation(title, description, start, end)
        Assert.assertEquals(toReturnByRepository, returnedFromService)
        Mockito.verify(repositoryMock).save(argThat<Reservations> {
            this.title == title && this.description == description
                    && this.start == start && this.end == end && this.user == null
        })
    }

    @Test
    fun deleteReservationById() {
        Mockito.`when`(repositoryMock.findById(15)).thenReturn(Optional.of(Reservations(reservationId = 15, title = "Title", description = "description",
                start = Timestamp(1561037749627L), end = Timestamp(1561037763719L))))
        service.deleteReservation(15)
        Mockito.verify(repositoryMock).deleteById(15)
    }

    @Test(expected = NotFoundException::class)
    fun deleteReservationByIdNotFound() {
        Mockito.`when`(repositoryMock.findById(15)).thenReturn(Optional.empty())
        service.deleteReservation(15)
    }

    @Test
    fun deleteReservationByObject() {
        val someReservation = Reservations(title = "Title", description = "Description",
                start = Timestamp(1561037749627L), end = Timestamp(1561037763719L))
        service.deleteReservation(someReservation)
        Mockito.verify(repositoryMock).delete(argThat { this == someReservation })
    }

    @Test
    fun assignUserToReservation() {
        val someReservation = Reservations(title = "Title", description = "Description",
                start = Timestamp(1561037749627L), end = Timestamp(1561037763719L))
        val userToSaveMock = Mockito.mock(User::class.java)
        val expectedReservation = Reservations(title = "Title", description = "Description",
                start = Timestamp(1561037749627L), end = Timestamp(1561037763719L), user = userToSaveMock)
        Mockito.`when`(repositoryMock.save<Reservations>(argThat {
            user == userToSaveMock
        })).thenReturn(expectedReservation)
        service.changeUserOfReservation(userToSaveMock, someReservation)
        Mockito.verify(repositoryMock).save(argThat<Reservations> {
            this.user == userToSaveMock && this == someReservation
        })
    }

    @Test
    fun addUserToNotReservedReservationSuccess() {
        val someReservation = Reservations(title = "Title", description = "Description",
                start = Timestamp(1561037749627L), end = Timestamp(1561037763719L))
        val userToInsertMock = Mockito.mock(User::class.java)
        Mockito.`when`(repositoryMock.findById(someReservation.reservationId)).thenReturn(Optional.of(someReservation))

        service.addUserToNotReservedReservation(userToInsertMock, someReservation.reservationId)
        Mockito.verify(repositoryMock).save(argThat<Reservations> {
            this.user == userToInsertMock && this === someReservation
        })
    }

    @Test
    fun tryToAddNameToAlreadyReserved() {
        val someReservation = Reservations(title = "Title", description = "Description",
                start = Timestamp(1561037749627L), end = Timestamp(1561037763719L))
        someReservation.user = Mockito.mock(User::class.java)
        Mockito.`when`(repositoryMock.findById(someReservation.reservationId)).thenReturn(Optional.of(someReservation))
        assertFailsWith<AlreadyBookedException> { service.addUserToNotReservedReservation(Mockito.mock(User::class.java), someReservation.reservationId) }
        Mockito.verify(repositoryMock, never()).save(any<Reservations>())
    }

    @Test
    fun tryAddNameToNotExistingReservation() {
        Mockito.`when`(repositoryMock.findById(15)).thenReturn(Optional.empty())
        assertFailsWith<NotFoundException> { service.addUserToNotReservedReservation(Mockito.mock(User::class.java), 15) }
        Mockito.verify(repositoryMock, never()).save(any<Reservations>())
    }
}
