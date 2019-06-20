package io.github.maksymilianrozanski.demo.dao

import io.github.maksymilianrozanski.demo.entity.TestTableEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TestTableRepository: JpaRepository<TestTableEntity, Int>