package io.github.maksymilianrozanski.demo.service

import io.github.maksymilianrozanski.demo.entity.Reservations
import java.sql.Timestamp

interface TestTableService {

    fun findAll(): List<Reservations>

    fun addReservation(title: String, description: String, start: Timestamp, end: Timestamp)

    fun deleteReservation(id: Int)

    fun deleteReservation(reservation: Reservations)

    fun assignNameToReservation(name: String, reservation: Reservations)
}