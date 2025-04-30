package com.hseun.lendy.network

import com.hseun.lendy.home.data.CreditResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CreditApi {
    @GET("/credit")
    suspend fun getMyCredit(@Header("Authorization") token: String): Response<CreditResponse>
}