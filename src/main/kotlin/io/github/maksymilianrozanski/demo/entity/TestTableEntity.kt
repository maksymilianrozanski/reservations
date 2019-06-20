package io.github.maksymilianrozanski.demo.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity(name = "testtable")
data class TestTableEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,
        @get:NotBlank val title: String = "",
        @get:NotBlank val description: String = ""
)