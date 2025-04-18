package com.hseun.lendy.auth.splash.viewmodel

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
class SplashViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    var isAutoLoginCheck by mutableStateOf(false)
    var isAutoLoginSuccess by mutableStateOf(false)

    fun autoLogin() {
        viewModelScope.launch {
            isAutoLoginSuccess = repository.autoLogin()
            isAutoLoginCheck = true
        }
    }
}