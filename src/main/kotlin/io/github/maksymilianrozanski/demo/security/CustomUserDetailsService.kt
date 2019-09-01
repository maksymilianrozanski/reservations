package io.github.maksymilianrozanski.demo.security

import io.github.maksymilianrozanski.demo.dao.UserRepository
import io.github.maksymilianrozanski.demo.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = findUser(username)
        return CustomUserDetails(user)
    }

    fun currentUser(): User {
        val currentUsername = SecurityContextHolder.getContext().authentication.name
        return findUser(currentUsername)
    }

    private fun findUser(username: String): User {
        return userRepository.findOneByUsername(username)
                ?: throw UsernameNotFoundException("User with name: $username not found.")
    }

    fun currentUserRoles(): List<String> {
        return SecurityContextHolder.getContext().authentication.authorities.stream().map {
            it.authority
        }.collect(Collectors.toList())
    }
}
