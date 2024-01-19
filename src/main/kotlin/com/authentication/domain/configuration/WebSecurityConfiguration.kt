package com.authentication.domain.configuration

import com.authentication.application.security.CustomAuthenticationFilter
import com.authentication.application.security.CustomAuthorizationFilter
import com.authentication.domain.service.CustomReactiveAuthenticationManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
//
//@Configuration
//@EnableWebFluxSecurity
//@SuppressWarnings
//class WebSecurityConfiguration {
//
//    @Autowired
//    private lateinit var customReactiveAuthenticationManager: CustomReactiveAuthenticationManager
//
//    @Autowired
//    private lateinit var customAuthenticationFilter: CustomAuthenticationFilter
//
//    @Bean
//    fun getSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
//        return http
//            .authorizeExchange { exchanges ->
//                exchanges
//                    .pathMatchers(
//                        "/api/auth/**",
//                        "/api/auth/login-token",
//                        "/api/v1/users/signup",
//                        "/configuration/ui",
//                        "/swagger-resources/**",
//                        "/configuration/**",
//                        "/swagger-ui.html",
//                        "/webjars/**",
//                        "/swagger-ui/**",
//                        "/v3/api-docs/**",
//                        "/api/v1/mp-order/webhook"
//                    ).permitAll()
//                    .anyExchange().authenticated()
//            }
//            .csrf { csrf ->
//                csrf.disable()
//            }
//            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
//            .authenticationManager(customReactiveAuthenticationManager)
//            .addFilterAt(customAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
//            .build()
//    }
//}