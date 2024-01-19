package com.authentication.application.security

import com.authentication.domain.model.auth.AuthApplicationDTO
import com.authentication.domain.model.auth.IAuthDTO
import com.authentication.infrastruture.repository.IApplicationSecretAuthorityRepository
import com.authentication.utils.JWTUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class JWTAuthenticationConverter @Autowired constructor(
    private val applicationSecretAuthorityRepository: IApplicationSecretAuthorityRepository
): ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        return Mono.justOrEmpty(exchange.request.headers[JWTUtils.HEADER_STRING]?.firstOrNull())
            .mapNotNull { token ->
                JWTUtils.verify(token!!)
            }.flatMap { authDTO ->
                getRoles(authDTO!!)
                    .map { authorities ->
                        authDTO.toAuthentication(authorities).also {
                            it.isAuthenticated = true
                        }
                    }
            }
    }

    private fun handleError(error: Throwable): Mono<Authentication> {
        return Mono.error(error)
    }

    private fun getRoles(authDTO: IAuthDTO): Mono<List<SimpleGrantedAuthority>> {
        return Flux.merge(
            Flux.just(SimpleGrantedAuthority("USER")),
            getClientIDRole(authDTO)
        ).collectList()
    }

    private fun getClientIDRole(authDTO: IAuthDTO): Flux<SimpleGrantedAuthority> {
        if(authDTO !is AuthApplicationDTO){
            return Flux.empty()
        }
        return applicationSecretAuthorityRepository.findAllById(listOf(authDTO.clientId)).map {
            SimpleGrantedAuthority(it.cdAuthority)
        }
    }
}