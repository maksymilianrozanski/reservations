package io.github.maksymilianrozanski.demo.security.contextholder

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.stream.Collectors

interface RolesProvider {
    fun getRoles(): List<String>
}

@Component
class RolesProviderImpl : RolesProvider {

    override fun getRoles(): List<String> {
        return SecurityContextHolder.getContext().authentication.authorities.stream().map {
            it.authority
        }.collect(Collectors.toList())
    }
}
