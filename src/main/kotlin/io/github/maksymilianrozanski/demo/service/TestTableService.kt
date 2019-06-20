package io.github.maksymilianrozanski.demo.service

import io.github.maksymilianrozanski.demo.entity.Reservations

interface TestTableService {

    fun findAll(): List<Reservations>
}