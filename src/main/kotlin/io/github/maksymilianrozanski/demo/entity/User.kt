package io.github.maksymilianrozanski.demo.entity

import javax.persistence.*

@Entity
data class User(var firstName: String = "",
                var lastName: String = "",
                var username: String = "",
                var email: String = "",
                var password: String = "") {

    @Id
    @GeneratedValue
    var userId: Long = 0

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            inverseJoinColumns = [JoinColumn(name = "roles_id")],
            joinColumns = [JoinColumn(name = "user_id")])
    var roles: List<Role> = listOf()

    @OneToMany(mappedBy = "user")
    var reservations: List<Reservations> = listOf()
}
