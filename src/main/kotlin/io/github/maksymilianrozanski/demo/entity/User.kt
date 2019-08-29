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
    var id: Long = 0

    @OneToMany(fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL))
    var roles: Set<Role> = HashSet()
}
