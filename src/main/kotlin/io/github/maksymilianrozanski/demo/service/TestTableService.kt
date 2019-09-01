package io.github.maksymilianrozanski.demo.service

import io.github.maksymilianrozanski.demo.entity.Reservations
import java.sql.Timestamp

interface TestTableService {

    fun findAll(): List<Reservations>

    fun findUnoccupiedReservations(): List<Reservations>

    fun findById(id: Int): Reservations

    fun addReservation(title: String, description: String, start: Timestamp, end: Timestamp): Reservations

    fun deleteReservation(id: Int)

    fun deleteReservation(reservation: Reservations)

    fun updateNameOfReservation(name: String, reservation: Reservations)

    fun addNameToNotReservedReservation(name: String, reservationId: Int): Reservations
}
