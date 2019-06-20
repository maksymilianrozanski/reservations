package io.github.maksymilianrozanski.demo.service

import io.github.maksymilianrozanski.demo.dao.TestTableRepository
import io.github.maksymilianrozanski.demo.entity.TestTableEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TestTableServiceImpl : TestTableService {

    @Autowired
    lateinit var repository: TestTableRepository

    override fun findAll(): List<TestTableEntity> {
        return repository.findAll()
    }
}