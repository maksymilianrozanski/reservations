package io.github.maksymilianrozanski.demo.dao

import io.github.maksymilianrozanski.demo.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByRoleName(roleName: String): Role

}
