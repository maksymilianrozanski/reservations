package io.github.maksymilianrozanski.demo.rest

import io.github.maksymilianrozanski.demo.entity.Reservations
import io.github.maksymilianrozanski.demo.security.contextholder.RolesProvider
import io.github.maksymilianrozanski.demo.service.AlreadyBookedException
import io.github.maksymilianrozanski.demo.service.NotFoundException
import io.github.maksymilianrozanski.demo.service.ReservationsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:4200"])
@RestController
@RequestMapping("/api")
class MyRestController(@Autowired var service: ReservationsService) {

    @Autowired
    lateinit var roles: RolesProvider

    @GetMapping("/roles")
    fun getUserRoles(): List<String> {
        return roles.getRoles()
    }

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

    @DeleteMapping("/reservations/{id}")
    fun deleteReservation(@PathVariable(value = "id") id: Int): ResponseEntity<Void> {
        return try {
            service.deleteReservation(id)
            ResponseEntity(HttpStatus.OK)   //TODO: return with status 204 / no content
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/book")
    fun bookReservation(@RequestBody reservation: Reservations): ResponseEntity<Reservations> {
        return try {
            ResponseEntity.ok(service.addNameToNotReservedReservation(reservation.user, reservation.id))
        } catch (notFound: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (alreadyBooked: AlreadyBookedException) {
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }
}
