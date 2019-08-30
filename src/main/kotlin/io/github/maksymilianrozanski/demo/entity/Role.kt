package io.github.maksymilianrozanski.demo.entity

import javax.persistence.*

@Entity
data class Role(
        var roleName: String = "") {

    @Id
    @GeneratedValue
    val id: Long = 0

    @ManyToMany
    @JoinTable(name = "user_roles",
            inverseJoinColumns = [JoinColumn(name = "user_id")],
            joinColumns = [JoinColumn(name = "roles_id")])
    val users: List<User> = listOf()
}
