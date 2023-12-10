package com.authentication.domain.service

import com.authentication.domain.model.auth.AuthenticationVO
import com.authentication.domain.model.exception.Exceptions
import com.authentication.utils.Sha512PasswordEncoder
import com.authentication.infrastruture.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class AuthClientService @Autowired constructor(
    private val passwordEncoder: PasswordEncoder = Sha512PasswordEncoder(),
    private val userRepository: UserRepository
) {

    fun authenticate(authenticationVO: AuthenticationVO): Mono<Authentication> {
        return Mono.just(authenticationVO).flatMap { authentication ->
            val username = authentication.principal
            val password = authentication.credentials
            userRepository.findByUsernameWithAddress(username)
                .mapNotNull { client ->
                    if(!passwordEncoder.matches(password, client.password)) throw Exceptions.BadCredentialsException("Usuário ou senha errados.")
                authentication.isAuthenticated = true
                authentication
            }
                .switchIfEmpty(Mono.error(Exceptions.BadCredentialsException("Usuário ou senha errados.")))
                .toMono()
        }
    }
}