package com.authentication.utils

import org.junit.jupiter.api.Test

class Sha512PasswordEncoderTest {

    @Test
    fun `Password encode test`() {
        val encoderPassword : Sha512PasswordEncoder = Sha512PasswordEncoder()

        val response = encoderPassword.encode("admin")
        println(response)
    }
}