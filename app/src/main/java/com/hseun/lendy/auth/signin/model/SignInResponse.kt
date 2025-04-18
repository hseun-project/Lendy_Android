package com.hseun.lendy.auth.signin.model

data class SignInResponse(
    val accessToken: String,
    val refreshToken: String
)
