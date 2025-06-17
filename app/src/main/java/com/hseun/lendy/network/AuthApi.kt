package com.hseun.lendy.network

import com.hseun.lendy.auth.signin.model.SignInRequest
import com.hseun.lendy.auth.signin.model.SignInResponse
import com.hseun.lendy.auth.signup.data.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<Unit>

    @POST("/auth/signin")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>

    @POST("/auth/refresh")
    suspend fun refresh(@Header("Authorization") token: String): Response<SignInResponse>

    @POST("/auth/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Unit>
}