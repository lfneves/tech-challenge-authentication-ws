package com.authentication.application.controller.v1


import com.authentication.domain.model.auth.AuthApplicationDTO
import com.authentication.domain.model.auth.AuthClientDTO
import com.authentication.domain.model.auth.AuthTokenDTO
import com.authentication.utils.JWTUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    private lateinit var authenticationManager: ReactiveAuthenticationManager

    @PostMapping("/login-token")
    @Operation(summary = "Authenticação usuário + senha",
        description = "Se authenticado corretamente, retorna o token de Authorização",
        tags = ["Autorização"]
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "202", description = "Successful login token."),
            ApiResponse(responseCode = "404", description = "Error user does not exist.", content = arrayOf(Content(schema = Schema(hidden = true)))),
            ApiResponse(responseCode = "500", description = "Error application can't find user.", content = arrayOf(Content(schema = Schema(hidden = true)))),
        ]
    )
    fun token(@RequestBody authDTO: AuthClientDTO): Mono<AuthTokenDTO> {
        return Mono.just(authDTO).flatMap {
            authenticationManager.authenticate(it.toAuthentication())
        }.map {
            val token = JWTUtils.createToken(authDTO)
            AuthTokenDTO(token)
        }
    }

    @PostMapping("/token-by-application")
    @Operation(summary = "Authenticação clientID + secretID",
        description = "Se authenticado corretamente, retorno o token de Authorização nas headers",
        tags = ["Autorização"]
    )
    fun tokenByApplication(@RequestBody authDTO: AuthApplicationDTO): Mono<AuthTokenDTO> {
        return Mono.just(authDTO).flatMap {
            authenticationManager.authenticate(it.toAuthentication())
        }.map {
            val token = JWTUtils.createToken(authDTO)
            AuthTokenDTO(token)
        }
    }

    @GetMapping("/")
    @ResponseBody
    fun home(): String {
        return "Welcome to the Kotlin Spring Boot OAuth2 application!"
    }

}