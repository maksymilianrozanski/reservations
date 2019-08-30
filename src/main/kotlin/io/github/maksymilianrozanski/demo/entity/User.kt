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

    //TODO: set proper cascade type
    @ManyToMany(fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL))
    @JoinTable(name = "user_roles",
            inverseJoinColumns = [JoinColumn(name = "roles_id")],
            joinColumns = [JoinColumn(name = "user_id")])
    var roles: List<Role> = listOf()
}
