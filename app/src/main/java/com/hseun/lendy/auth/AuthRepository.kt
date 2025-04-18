package com.hseun.lendy.auth

import android.util.Log
import com.hseun.lendy.auth.signin.model.SignInRequest
import com.hseun.lendy.auth.signin.model.SignInResponse
import com.hseun.lendy.auth.signup.data.SignUpRequest
import com.hseun.lendy.auth.verification.data.VerificationResponse
import com.hseun.lendy.local.AuthPreference
import com.hseun.lendy.network.AuthApi
import com.hseun.lendy.network.OpenApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val openApi: OpenApi,
    private val pref: AuthPreference
) {
    suspend fun signUp(userId: String, password: String): Result<Unit> {
        return try {
            val response = authApi.signUp(SignUpRequest(userId, password))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else if (response.code() == 409) {
                Result.failure(Exception("Overlap userId"))
            } else {
                Log.e("signup", response.code().toString())
                Result.failure(Exception("Sign up failed"))
            }
        } catch (e: Exception) {
            Log.e("signup", e.message.toString())
            Result.failure(e)
        }
    }

    suspend fun getVerificationUrl(userId: String): Result<String> {
        return try {
            val response = openApi.verifyUser(userId)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it.authUrl) }
                    ?: Result.failure(Exception("반환값 없음"))
            } else {
                Result.failure(Exception("url 반환 실패"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signIn(userId: String, password: String): Result<Unit> {
        return try {
            val response = authApi.signIn(SignInRequest(userId, password))
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    saveToken(responseBody.accessToken, responseBody.refreshToken)
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("응답 바디 없음"))
                }
            } else {
                Result.failure(Exception("로그인 실패 ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception(e))
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