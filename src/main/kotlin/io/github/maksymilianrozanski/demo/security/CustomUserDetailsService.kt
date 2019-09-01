package io.github.maksymilianrozanski.demo.security

import io.github.maksymilianrozanski.demo.dao.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findOneByUsername(username)
                ?: throw UsernameNotFoundException("User with name: $username not found.")
        return CustomUserDetails(user)
    }

    fun currentUserRoles(): List<String>{
        return SecurityContextHolder.getContext().authentication.authorities.stream().map {
            it.authority
        }.collect(Collectors.toList())
    }
}
