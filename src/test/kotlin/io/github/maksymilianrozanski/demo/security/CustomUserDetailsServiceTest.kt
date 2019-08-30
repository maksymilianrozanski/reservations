package io.github.maksymilianrozanski.demo.security

import io.github.maksymilianrozanski.demo.dao.UserRepository
import io.github.maksymilianrozanski.demo.entity.User
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.springframework.security.core.userdetails.UsernameNotFoundException
import kotlin.test.assertFailsWith


internal class CustomUserDetailsServiceTest {

    @Test
    fun loadUserByUsernameSuccess() {
        val userMock = Mockito.mock(User::class.java)
        val userRepositoryMock = Mockito.mock(UserRepository::class.java)
        Mockito.`when`(userRepositoryMock.findOneByUsername("someName")).thenReturn(userMock)

        val userDetailsService = CustomUserDetailsService(userRepositoryMock)
        val userDetails = userDetailsService.loadUserByUsername("someName") as CustomUserDetails
        Assert.assertEquals(userMock, userDetails.user)
    }

    @Test
    fun loadUserByUsernameNotFound() {
        val userRepositoryMock = Mockito.mock(UserRepository::class.java)
        Mockito.`when`(userRepositoryMock.findOneByUsername("someName")).thenReturn(null)

        val userDetailsService = CustomUserDetailsService(userRepositoryMock)
        assertFailsWith<UsernameNotFoundException>(message = "User with name: someName not found.",
                block = { userDetailsService.loadUserByUsername("someName") })
    }
}
