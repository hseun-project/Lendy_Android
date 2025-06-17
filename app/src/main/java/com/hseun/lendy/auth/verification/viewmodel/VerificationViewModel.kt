package com.hseun.lendy.auth.verification.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    var url by mutableStateOf("")

    var isNavigateToLogin by mutableStateOf(false)
    var isGetUrl by mutableStateOf<Boolean?>(null)
    var isLoading by mutableStateOf(false)

    fun onUrlLoaded(url: String) {
        if (url.startsWith("http://localhost:8080/open")) {
            isNavigateToLogin = true
        }
    }

    fun getStartUrl(userId: String) {
        viewModelScope.launch {
            isLoading = true
            val result = repository.getVerificationUrl(userId)
            isLoading = false
            result.onSuccess {
                url = it.authUrl
                isGetUrl = true
            }.onFailure {
                isGetUrl = false
            }
        }
    }
}