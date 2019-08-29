package io.github.maksymilianrozanski.demo.security

import io.github.maksymilianrozanski.demo.dao.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
open class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return CustomUserDetails(userRepository.findOneByUsername(username)!!)
        //TODO: can throw null pointer exception
    }
}
