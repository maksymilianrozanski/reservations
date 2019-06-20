package io.github.maksymilianrozanski.demo.service

import io.github.maksymilianrozanski.demo.dao.TestTableRepository
import io.github.maksymilianrozanski.demo.entity.Reservations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TestTableServiceImpl : TestTableService {

    @Autowired
    lateinit var repository: TestTableRepository

    override fun findAll(): List<Reservations> {
        return repository.findAll()
    }
}