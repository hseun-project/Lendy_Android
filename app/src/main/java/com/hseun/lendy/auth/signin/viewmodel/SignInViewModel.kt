package com.hseun.lendy.auth.signin.viewmodel

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
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    var userId by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var isSignInSuccess by mutableStateOf<Boolean?>(null)

    fun onIdChanged(input: String) {
        userId = input
        if (isSignInSuccess != null) {
            isSignInSuccess = null
        }
    }
    fun onPasswordChanged(input: String) {
        password = input
        if (isSignInSuccess != null) {
            isSignInSuccess = null
        }
    }

    fun onSignInClick() {
        viewModelScope.launch {
            isLoading = true
            val result = repository.signIn(userId, password)
            isLoading = false
            result.onSuccess {
                isSignInSuccess = true
            }.onFailure {
                isSignInSuccess = false
            }
        }
    }
}