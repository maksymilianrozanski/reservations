package io.github.maksymilianrozanski.demo.rest

import io.github.maksymilianrozanski.demo.entity.TestTableEntity
import io.github.maksymilianrozanski.demo.service.TestTableService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class MyRestController(@Autowired var service: TestTableService) {


    @GetMapping("/findAll")
    fun findAll(): List<TestTableEntity> {
        println("find all called")
        return service.findAll()
    }
}