package io.github.maksymilianrozanski.demo.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.never
import io.github.maksymilianrozanski.demo.dao.TestTableRepository
import io.github.maksymilianrozanski.demo.entity.Reservations
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.sql.Timestamp
import java.util.*
import kotlin.test.assertFailsWith

internal class TestTableServiceImplTest {

    lateinit var service: TestTableServiceImpl
    @Mock
    lateinit var repositoryMock: TestTableRepository

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        service = TestTableServiceImpl()
        service.repository = repositoryMock
    }

    @Test
    fun findAll() {
        service.findAll()
        Mockito.verify(repositoryMock).findAll()
    }

    @Test
    fun findByIdSuccess() {
        val title = "Example title"
        val description = "Example description"
        val start = Timestamp(1561037749627L)
        val end = Timestamp(1561037763719L)
        val toReturnByRepository = Reservations(id = 10, title = title, description = description,
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
        val toReturnByRepository = Reservations(id = 10, title = title, description = description,
                start = start, end = end)
        Mockito.`when`(repositoryMock.save(argThat<Reservations> {
            this.title == title && this.description == description
                    && this.start == start && this.end == end
        })).thenReturn(toReturnByRepository)
        val returnedFromService = service.addReservation(title, description, start, end)
        Assert.assertEquals(toReturnByRepository, returnedFromService)
        Mockito.verify(repositoryMock).save(argThat<Reservations> {
            this.title == title && this.description == description
                    && this.start == start && this.end == end && this.user == ""
        })
    }

    @Test
    fun deleteReservationById() {
        Mockito.`when`(repositoryMock.findById(15)).thenReturn(Optional.of(Reservations(id = 15, title = "Title", description = "description",
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
    fun assignNameToReservation() {
        val someReservation = Reservations(title = "Title", description = "Description",
                start = Timestamp(1561037749627L), end = Timestamp(1561037763719L))
        val nameToSave = "Name to assign"
        service.updateNameOfReservation(nameToSave, someReservation)
        Mockito.verify(repositoryMock).save(argThat<Reservations> {
            this.user == nameToSave && this === someReservation
        })
    }

    @Test
    fun addNameToNotReservedReservationSuccess() {
        val someReservation = Reservations(title = "Title", description = "Description",
                start = Timestamp(1561037749627L), end = Timestamp(1561037763719L))
        val nameToInsert = "Name to insert"
        Mockito.`when`(repositoryMock.findById(someReservation.id)).thenReturn(Optional.of(someReservation))

        service.addNameToNotReservedReservation(nameToInsert, someReservation.id)
        Mockito.verify(repositoryMock).save(argThat<Reservations> {
            this.user == nameToInsert && this === someReservation
        })
    }

    @Test
    fun tryToAddNameToAlreadyReserved() {
        val someReservation = Reservations(title = "Title", description = "Description",
                start = Timestamp(1561037749627L), end = Timestamp(1561037763719L))
        someReservation.user = "Already taken"
        Mockito.`when`(repositoryMock.findById(someReservation.id)).thenReturn(Optional.of(someReservation))
        assertFailsWith<AlreadyBookedException> { service.addNameToNotReservedReservation("Name", someReservation.id) }
        Mockito.verify(repositoryMock, never()).save(any<Reservations>())
    }

    @Test
    fun tryAddNameToNotExistingReservation() {
        Mockito.`when`(repositoryMock.findById(15)).thenReturn(Optional.empty())
        assertFailsWith<NotFoundException> { service.addNameToNotReservedReservation("Name", 15) }
        Mockito.verify(repositoryMock, never()).save(any<Reservations>())
    }
}