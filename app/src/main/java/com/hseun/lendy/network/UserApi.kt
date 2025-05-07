package com.hseun.lendy.network

import com.hseun.lendy.mypage.data.MyApplyLoanListItemData
import com.hseun.lendy.mypage.data.MyPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {
    @GET("/user/info")
    suspend fun getMyInfo(@Header("Authorization") token: String): Response<MyPageResponse>

    @GET("/user/apply/loans")
    suspend fun getMyApplyLoans(@Header("Authorization") token: String): Response<List<MyApplyLoanListItemData>>
}