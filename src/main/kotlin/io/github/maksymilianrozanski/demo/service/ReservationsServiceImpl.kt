package io.github.maksymilianrozanski.demo.service

import io.github.maksymilianrozanski.demo.dao.ReservationsRepository
import io.github.maksymilianrozanski.demo.entity.Reservations
import io.github.maksymilianrozanski.demo.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.stream.Collectors

@Service
class ReservationsServiceImpl : ReservationsService {

    @Autowired
    lateinit var repository: ReservationsRepository

    override fun findAll(): List<Reservations> {
        return repository.findAll()
    }

    override fun findUnoccupiedReservations(): List<Reservations> {
        return repository.findAll().stream()
                .filter { reservation -> reservation.user == null }.collect(Collectors.toList())
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
        repository.deleteById(reservation.reservationId)
    }

    override fun deleteReservation(reservation: Reservations) {
        repository.delete(reservation)
    }

    override fun editReservation( reservation: Reservations): Reservations {
        return repository.save(reservation)
    }

    override fun addUserToNotReservedReservation(user: User, reservationId: Int): Reservations {
        val optionalReservation = repository.findById(reservationId)
        if (optionalReservation.isPresent) {
            val reservation = optionalReservation.get()
            if (reservation.user == null) {
                reservation.user = user
                repository.save(reservation)
                return reservation
            } else throw AlreadyBookedException("Reservation with id: $reservationId is already booked")
        }
        throw NotFoundException("Not found record with id: $reservationId")
    }
}

class NotFoundException(message: String) : Exception(message)

class AlreadyBookedException(message: String) : Exception(message)
