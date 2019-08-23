package io.github.maksymilianrozanski.demo.entity

import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "reservations")
data class Reservations(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,
        val title: String = "",
        val description: String = "",
        var user: String = "",
        val start: Timestamp = Timestamp(0),
        val end: Timestamp = Timestamp(0)
)
