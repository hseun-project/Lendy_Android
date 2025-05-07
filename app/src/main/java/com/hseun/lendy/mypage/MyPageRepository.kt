package com.hseun.lendy.mypage

import android.util.Log
import com.hseun.lendy.local.AuthPreference
import com.hseun.lendy.mypage.data.MyApplyLoanListItemData
import com.hseun.lendy.mypage.data.MyPageResponse
import com.hseun.lendy.network.AuthApi
import com.hseun.lendy.network.UserApi

class MyPageRepository(
    private val api: UserApi,
    private val authApi: AuthApi,
    private val pref: AuthPreference
) {
    private val token = pref.getAccessToken() ?: "token"

    suspend fun getMyInfo(): Result<MyPageResponse> {
        return try {
            val response = api.getMyInfo(token)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Log.e("getMyInfo", response.code().toString())
                    Result.failure(Exception("응답 바디 없음"))
                }
            } else {
                Result.failure(Exception("MyInfo Get Failed"))
            }
        } catch (e: Exception) {
            Log.e("getMyInfo", e.message.toString())
            Result.failure(e)
        }
    }

    suspend fun getMyApplyLoan(): Result<List<MyApplyLoanListItemData>> {
        return try {
            val response = api.getMyApplyLoans(token)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("응답 바디 없음"))
                }
            } else {
                Result.failure(Exception("MyApplyLoan Get Failed"))
            }
        } catch (e: Exception) {
            Log.e("getMyApplyLoan", e.message.toString())
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<Unit> {
        return try {
            val response = authApi.logout(token)
            if (response.isSuccessful) {
                pref.clearAccessToken()
                pref.clearRefreshToken()
                Result.success(Unit)
            } else {
                Result.failure(Exception("로그아웃 실패"))
            }
        } catch (e: Exception) {
            Log.e("logout", e.message.toString())
            Result.failure(e)
        }
    }
}