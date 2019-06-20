package io.github.maksymilianrozanski.demo.service

import io.github.maksymilianrozanski.demo.entity.TestTableEntity

interface TestTableService {

    fun findAll(): List<TestTableEntity>
}