package io.github.maksymilianrozanski.demo.rest

import io.github.maksymilianrozanski.demo.entity.Reservations
import io.github.maksymilianrozanski.demo.security.CustomUserDetailsService
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
    lateinit var userDetailsService: CustomUserDetailsService

    @GetMapping("/roles")
    fun getUserRoles(): List<String> {
        return userDetailsService.currentUserRoles()
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

    @PutMapping("/reservations")
    fun editReservation(@RequestBody reservation: Reservations): ResponseEntity<Reservations> {
        try {
            if (userDetailsService.currentUserRoles().contains("Role(roleName=ADMIN)")) {
                return ResponseEntity.ok(service.editReservation(reservation))
            }
            //Not admin
            else if (userDetailsService.currentUserRoles().contains("Role(roleName=USER)")) {
                if (userDetailsService.currentUser().username == service.findById(reservation.reservationId)?.user?.username ?: false) {
                    //Already booked by this user
                    return ResponseEntity(HttpStatus.NO_CONTENT)
                }
                return ResponseEntity.ok(service.addUserToNotReservedReservation(userDetailsService.currentUser(), reservation.reservationId))
            } else {
                //User without any role
                return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
            }
        } catch (notFound: NotFoundException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (alreadyBooked: AlreadyBookedException) {
            return ResponseEntity(HttpStatus.CONFLICT)
        }
    }
}
