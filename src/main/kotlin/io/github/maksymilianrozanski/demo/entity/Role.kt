package io.github.maksymilianrozanski.demo.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Role(var roleName: String = "") {

    @Id
    @GeneratedValue
    val id: Long? = null

}
