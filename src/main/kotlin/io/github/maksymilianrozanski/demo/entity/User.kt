package io.github.maksymilianrozanski.demo.entity

import javax.persistence.*

@Entity
class User(var firstName: String = "",
                var lastName: String = "",
                var userName: String = "",
                var email: String = "",
        //TODO: rename variables
                var passWord: String = "") {

    @Id
    @GeneratedValue
    var id: Long = 0
    var version: Int = 0
    var accountNonExpired = true
    var accountNonLocked = true
    var credentialsNonExpired = true
    var enabled = true
    @OneToMany(fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL))
    var roles: Set<Role> = HashSet()
}
