package io.github.maksymilianrozanski.demo.service

import io.github.maksymilianrozanski.demo.dao.TestTableRepository
import io.github.maksymilianrozanski.demo.entity.Reservations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class TestTableServiceImpl : TestTableService {

    @Autowired
    lateinit var repository: TestTableRepository

    override fun findAll(): List<Reservations> {
        return repository.findAll()
    }

    override fun addReservation(title: String, description: String, start: Timestamp, end: Timestamp) {
        repository.save(Reservations(title = title, description = description, start = start, end = end))
    }

    override fun deleteReservation(id: Int) {
        repository.deleteById(id)
    }

    override fun deleteReservation(reservation: Reservations) {
        repository.delete(reservation)
    }

    override fun assignNameToReservation(name: String, reservation: Reservations) {
        reservation.user = name
        repository.save(reservation)
    }
}