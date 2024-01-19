package com.authentication.application.security

import com.authentication.domain.service.CustomReactiveAuthenticationManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationFilter @Autowired constructor(
    reactiveAuthenticationManager: CustomReactiveAuthenticationManager,
    customReactiveAuthenticationConverter: CustomReactiveAuthenticationConverter
): AuthenticationWebFilter(reactiveAuthenticationManager) {

    init {
        this.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        this.setServerAuthenticationConverter(customReactiveAuthenticationConverter)
        this.setAuthenticationSuccessHandler { webFilterExchange, authentication ->
            webFilterExchange.chain.filter(webFilterExchange.exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
        }
    }
}