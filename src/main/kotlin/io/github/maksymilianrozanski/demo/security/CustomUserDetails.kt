package io.github.maksymilianrozanski.demo.security

import io.github.maksymilianrozanski.demo.entity.User
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

open class CustomUserDetails : User, UserDetails {

    private val log = LoggerFactory.getLogger(CustomUserDetails::class.java)

    constructor(user: User) : super(user)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.stream()
                .map { role ->
                    log.debug("Granting Authority to user with role: $role")
                    SimpleGrantedAuthority(role.toString())
                }
                .collect(Collectors.toList())
    }

    override fun isEnabled(): Boolean {
        return super.enabled
    }

    override fun getUsername(): String {
        return super.userName
    }

    override fun isCredentialsNonExpired(): Boolean {
        return super.credentialsNonExpired
    }

    override fun getPassword(): String {
        return super.passWord
    }

    override fun isAccountNonExpired(): Boolean {
        return super.accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return super.accountNonLocked
    }
}
