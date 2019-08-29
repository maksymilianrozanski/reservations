package io.github.maksymilianrozanski.demo.security

import io.github.maksymilianrozanski.demo.entity.User
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

open class CustomUserDetails(val user: User) : UserDetails {

    private val log = LoggerFactory.getLogger(CustomUserDetails::class.java)

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user.roles.stream()
                .map { role ->
                    log.debug("Granting Authority to user with role: $role")
                    SimpleGrantedAuthority(role.toString())
                }
                .collect(Collectors.toList())
    }

    override fun isEnabled(): Boolean {
        return user.enabled
    }

    override fun getUsername(): String {
        return user.userName
    }

    override fun isCredentialsNonExpired(): Boolean {
        return user.credentialsNonExpired
    }

    override fun getPassword(): String {
        return user.passWord
    }

    override fun isAccountNonExpired(): Boolean {
        return user.accountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return user.accountNonLocked
    }
}
