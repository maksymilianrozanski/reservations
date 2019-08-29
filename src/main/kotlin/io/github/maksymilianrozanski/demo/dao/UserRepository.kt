package io.github.maksymilianrozanski.demo.dao

import io.github.maksymilianrozanski.demo.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findOneByUsername(userName: String): User?
}
