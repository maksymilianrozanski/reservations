package io.github.maksymilianrozanski.demo.entity

import javax.persistence.*

@Entity
open class User(open var firstName: String = "",
                open var lastName: String = "",
                open var userName: String = "",
                open var email: String = "",
        //TODO: rename variables
                open var passWord: String = "") {

    @Id
    @GeneratedValue
    var id: Long = 0
    open var version: Int = 0
    open var accountNonExpired = true
    open var accountNonLocked = true
    open var credentialsNonExpired = true
    open var enabled = true
    @OneToMany(fetch = FetchType.EAGER, cascade = arrayOf(CascadeType.ALL))
    open var roles: Set<Role> = HashSet()

    constructor(user: User) : this(user.firstName, user.lastName, user.userName, user.email, user.passWord) {
        id = user.id
        version = user.version
        firstName = user.firstName
        lastName = user.lastName
        userName = user.userName
        email = user.email
        passWord = user.passWord
        email = user.email
        passWord = user.passWord
        accountNonExpired = user.accountNonExpired
        accountNonLocked = user.accountNonLocked
        credentialsNonExpired = user.credentialsNonExpired
        enabled = user.enabled
        roles = user.roles
    }
}
