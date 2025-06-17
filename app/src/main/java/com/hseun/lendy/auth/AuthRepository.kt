package com.hseun.lendy.auth

import com.hseun.lendy.auth.signin.model.SignInRequest
import com.hseun.lendy.auth.signup.data.SignUpRequest
import com.hseun.lendy.auth.verification.data.VerificationResponse
import com.hseun.lendy.local.AuthPreference
import com.hseun.lendy.network.AuthApi
import com.hseun.lendy.network.OpenApi
import com.hseun.lendy.utils.apiCall
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val openApi: OpenApi,
    private val pref: AuthPreference
) {
    suspend fun signUp(userId: String, password: String): Result<Unit> {
        return apiCall("signUp") { authApi.signUp(SignUpRequest(userId, password)) }
    }

    suspend fun getVerificationUrl(userId: String): Result<VerificationResponse> {
        return apiCall("getVerificationUrl") { openApi.verifyUser(userId) }
    }

    suspend fun signIn(userId: String, password: String): Result<Unit> {
        return apiCall("signIn") {authApi.signIn(SignInRequest(userId, password))}
            .mapCatching { responseBody ->
                saveToken(responseBody.accessToken, responseBody.refreshToken)
            }
    }

    private fun getRefreshToken(): String? = pref.getRefreshToken()
    private fun saveToken(accessToken: String, refreshToken: String) {
        pref.setAccessToken(accessToken)
        pref.setRefreshToken(refreshToken)
    }
    private fun clearToken() {
        pref.clearAccessToken()
        pref.clearRefreshToken()
    }
    suspend fun autoLogin(): Boolean {
        val refreshToken = getRefreshToken() ?: return false
        return try {
            val response = authApi.refresh("Bearer $refreshToken")
            if (response.isSuccessful) {
                response.body()?.let {
                    saveToken(it.accessToken, it.refreshToken)
                    true
                } ?: false
            } else {
                clearToken()
                false
            }
        } catch (e: Exception) {
            clearToken()
            false
        }
    }
}