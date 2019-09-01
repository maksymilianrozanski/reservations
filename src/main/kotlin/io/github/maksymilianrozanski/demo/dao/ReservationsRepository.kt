package io.github.maksymilianrozanski.demo.dao

import io.github.maksymilianrozanski.demo.entity.Reservations
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationsRepository: JpaRepository<Reservations, Int>
