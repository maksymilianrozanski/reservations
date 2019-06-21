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

    override fun findById(id: Int): Reservations {
        val optional = repository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else throw NotFoundException("Not found record with id: $id")
    }

    override fun addReservation(title: String, description: String, start: Timestamp, end: Timestamp): Reservations {
        return repository.save(Reservations(title = title, description = description, start = start, end = end))
    }

    override fun deleteReservation(id: Int) {
        val reservation = findById(id)
        repository.deleteById(reservation.id)
    }

    override fun deleteReservation(reservation: Reservations) {
        repository.delete(reservation)
    }

    override fun updateNameOfReservation(name: String, reservation: Reservations) {
        reservation.user = name
        repository.save(reservation)
    }

    override fun addNameToNotReservedReservation(name: String, reservationId: Int): Boolean {
        val optionalReservation = repository.findById(reservationId)
        if (optionalReservation.isPresent) {
            val reservation = optionalReservation.get()
            if (reservation.user == "") {
                reservation.user = name
                repository.save(reservation)
                return true
            }
        }
        return false
    }
}

class NotFoundException(message: String) : Exception(message)