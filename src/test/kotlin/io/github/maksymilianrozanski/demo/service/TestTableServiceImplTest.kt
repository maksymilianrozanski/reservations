package io.github.maksymilianrozanski.demo.service

import com.nhaarman.mockitokotlin2.argThat
import io.github.maksymilianrozanski.demo.dao.TestTableRepository
import io.github.maksymilianrozanski.demo.entity.Reservations
import org.junit.Test
import org.mockito.Mockito
import java.sql.Timestamp

internal class TestTableServiceImplTest {

    @Test
    fun findAll() {
        val service = TestTableServiceImpl()
        val repositoryMock = Mockito.mock(TestTableRepository::class.java)
        service.repository = repositoryMock

        service.findAll()
        Mockito.verify(repositoryMock).findAll()
    }

    @Test
    fun addReservation() {
        val service = TestTableServiceImpl()
        val repositoryMock = Mockito.mock(TestTableRepository::class.java)
        service.repository = repositoryMock

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