package com.hseun.lendy.network

import com.hseun.lendy.loan.apply.data.ApplyLoanListItemData
import com.hseun.lendy.loan.repay.data.LentRepayListItemData
import com.hseun.lendy.loan.repay.data.MyRepayListItemData
import com.hseun.lendy.utils.ApplyLoan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LoansApi {
    @GET("/loans/apply/{loanType}")
    suspend fun getApplyLoanRequest(
        @Header("Authorization") token: String,
        @Path("loanType") loanType: ApplyLoan
    ): Response<List<ApplyLoanListItemData>>

    @GET("/loans/repay")
    suspend fun getMyRepay(@Header("Authorization") token: String): Response<List<MyRepayListItemData>>

    @GET("/loans/lent")
    suspend fun getLentRepay(@Header("Authorization") token: String): Response<List<LentRepayListItemData>>
}