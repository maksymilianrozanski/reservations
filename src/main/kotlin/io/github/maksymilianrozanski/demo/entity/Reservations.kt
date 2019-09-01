package io.github.maksymilianrozanski.demo.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity(name = "reservations")
data class Reservations(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val reservationId: Int = 0,
        val title: String = "",
        val description: String = "",
        @ManyToOne
        @JoinColumn(name = "userId")
        var user: User? = null,
        val start: Timestamp = Timestamp(0),
        val end: Timestamp = Timestamp(0)
)
