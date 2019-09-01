package io.github.maksymilianrozanski.demo.service

import io.github.maksymilianrozanski.demo.entity.Reservations
import io.github.maksymilianrozanski.demo.entity.User
import java.sql.Timestamp

interface ReservationsService {

    fun findAll(): List<Reservations>

    fun findUnoccupiedReservations(): List<Reservations>

    fun findById(id: Int): Reservations

    fun addReservation(title: String, description: String, start: Timestamp, end: Timestamp): Reservations

    fun deleteReservation(id: Int)

    fun deleteReservation(reservation: Reservations)

    fun changeUserOfReservation(newUser: User, reservation: Reservations)

    fun addUserToNotReservedReservation(user: User, reservationId: Int): Reservations
}
