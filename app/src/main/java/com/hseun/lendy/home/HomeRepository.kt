package com.hseun.lendy.home

import android.util.Log
import com.hseun.lendy.home.data.CreditResponse
import com.hseun.lendy.loan.apply.data.ApplyLoanListItemData
import com.hseun.lendy.loan.repay.data.LentRepayListItemData
import com.hseun.lendy.loan.repay.data.MyRepayListItemData
import com.hseun.lendy.local.AuthPreference
import com.hseun.lendy.network.CreditApi
import com.hseun.lendy.network.LoansApi
import com.hseun.lendy.utils.ApplyLoan

class HomeRepository(
    private val api: LoansApi,
    private val creditApi: CreditApi,
    private val pref: AuthPreference
) {
    private val token = pref.getAccessToken() ?: "token"

    suspend fun getCredit(): Result<CreditResponse> {
        return try {
            val response = creditApi.getMyCredit(token)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("응답 바디 없음"))
                }
            } else {
                Result.failure(Exception("Credit Get Failed"))
            }
        } catch (e: Exception) {
            Log.e("getCredit", e.message.toString())
            Result.failure(e)
        }
    }

    suspend fun getApplyLoanRequestList(): Result<List<ApplyLoanListItemData>> {
        return try {
            val response = api.getApplyLoanRequestList(token, ApplyLoan.PRIVATE_LOAN)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("응답 바디 없음"))
                }
            } else {
                Log.e("getApplyLoanRequest", response.code().toString())
                Result.failure(Exception("Apply Loan Request Get failed"))
            }
        } catch (e: Exception) {
            Log.e("getApplyLoanRequest", e.message.toString())
            Result.failure(e)
        }
    }

    suspend fun getMyRepayList(): Result<List<MyRepayListItemData>> {
        return try {
            val response = api.getMyRepayList(token)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("응답 바디 없음"))
                }
            } else {
                Log.e("getMyRepay", response.code().toString())
                Result.failure(Exception("My Repay Get Failed"))
            }
        } catch (e: Exception) {
            Log.e("getMyRepay", e.message.toString())
            Result.failure(e)
        }
    }

    suspend fun getLentRepayList(): Result<List<LentRepayListItemData>> {
        return try {
            val response = api.getLentRepayList(token)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("응답 바디 없음"))
                }
            } else {
                Log.e("getLentRepay", response.code().toString())
                Result.failure(Exception("Lent Repay Get Failed"))
            }
        } catch (e: Exception) {
            Log.e("getLentRepay", e.message.toString())
            Result.failure(e)
        }
    }
}