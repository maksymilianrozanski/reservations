package io.github.maksymilianrozanski.demo.rest

import io.github.maksymilianrozanski.demo.entity.Reservations
import io.github.maksymilianrozanski.demo.service.TestTableService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class MyRestController(@Autowired var service: TestTableService) {


    @GetMapping("/reservations")
    fun findAll(): List<Reservations> {
        return service.findAll()
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    fun addNewReservation(@RequestBody reservation: Reservations): Reservations {
        return service.addReservation(title = reservation.title, description = reservation.description,
                start = reservation.start, end = reservation.end)
    }
}