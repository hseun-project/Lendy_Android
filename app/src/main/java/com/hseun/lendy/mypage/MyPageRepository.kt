package com.hseun.lendy.mypage

import com.hseun.lendy.local.AuthPreference
import com.hseun.lendy.mypage.data.MyApplyLoanListItemData
import com.hseun.lendy.mypage.data.MyPageResponse
import com.hseun.lendy.network.AuthApi
import com.hseun.lendy.network.UserApi
import com.hseun.lendy.utils.apiCall

class MyPageRepository(
    private val api: UserApi,
    private val authApi: AuthApi,
    private val pref: AuthPreference
) {
    private val token = pref.getAccessToken() ?: "token"

    suspend fun getMyInfo(): Result<MyPageResponse> {
        return apiCall("getMyInfo") { api.getMyInfo(token) }
    }

    suspend fun getMyApplyLoan(): Result<List<MyApplyLoanListItemData>> {
        return apiCall("getMyApplyLoan") { api.getMyApplyLoans(token) }
    }

    suspend fun logout(): Result<Unit> {
        val response = apiCall("logout") {authApi.logout(token)}
        if (response.isSuccess) {
            pref.clearAccessToken()
            pref.clearRefreshToken()
        }
        return response
    }
}