package io.github.maksymilianrozanski.demo.service

import com.nhaarman.mockitokotlin2.argThat
import io.github.maksymilianrozanski.demo.dao.TestTableRepository
import io.github.maksymilianrozanski.demo.entity.Reservations
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.sql.Timestamp

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
    fun addReservation() {
        val title = "Example title"
        val description = "Example description"
        val start = Timestamp(1561037749627L)
        val end = Timestamp(1561037763719L)
        service.addReservation(title, description, start, end)
        Mockito.verify(repositoryMock).save(argThat<Reservations> {
            this.title == title && this.description == description && this.start == start && this.end == end && this.user == ""
        })
    }
}