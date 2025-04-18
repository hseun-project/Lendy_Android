package com.hseun.lendy.network

import com.hseun.lendy.auth.verification.data.VerificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenApi {
    @GET("/open/{userId}")
    suspend fun verifyUser(@Path("userId") userId: String): Response<VerificationResponse>
}