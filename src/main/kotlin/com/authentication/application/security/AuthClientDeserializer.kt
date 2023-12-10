package com.authentication.application.security

import com.authentication.domain.model.auth.AuthClientDTO
import com.authentication.domain.model.auth.IAuthDTO
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException

object AuthClientDeserializer {

    fun getInstance(claims: Claims): IAuthDTO {
        if(claims.containsKey("username")){
            return AuthClientDTO(claims["idClient"].toString().toLong(), claims["username"].toString(), "")
        }
        throw JwtException("Não conseguimos deserializar o JWT")
    }
}
