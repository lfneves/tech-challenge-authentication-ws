package com.authentication.application.security

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.stereotype.Component
import org.springframework.web.filter.reactive.ServerWebExchangeContextFilter
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class CustomAuthorizationFilter : ServerWebExchangeContextFilter() {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        // Get the request path
        val path = exchange.request.uri.path

        // Perform authorization logic based on the path or any other attributes
        if (path.startsWith("/api/auth") && exchange.request.method == HttpMethod.POST) {
            return handleAuthorization(exchange, chain)
        }

        // If no specific authorization check is needed, continue the filter chain
        return chain.filter(exchange)
    }

    private fun handleAuthorization(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return ReactiveSecurityContextHolder.getContext()
            .flatMap { context: SecurityContext ->
                val authentication = context.authentication
                // Perform additional checks or modifications to the authentication object if needed

                // Example: Check if the user has a specific role
                if (authentication is UsernamePasswordAuthenticationToken &&
                    authentication.authorities.any { it.authority == "ROLE_ADMIN" }
                ) {
                    // Authorized, continue the filter chain
                    chain.filter(exchange)
                } else {
                    // Not authorized, return an error response or handle accordingly
                    // For example, return a forbidden response
                    exchange.response.statusCode = HttpStatus.FORBIDDEN
                    exchange.response.setComplete()
                }
            }
    }
}
