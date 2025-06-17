package com.hseun.lendy.auth.signup.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy.auth.AuthRepository
import com.hseun.lendy.utils.InputErrorType
import com.hseun.lendy.utils.InputRegexType
import com.hseun.lendy.utils.checkInputRegex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    var userId by mutableStateOf("")
    var password by mutableStateOf("")
    var checkPassword by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var isSignUpSuccess by mutableStateOf(false)

    var idErrorType by mutableStateOf(InputErrorType.NONE)
    var pwErrorType by mutableStateOf(InputErrorType.NONE)
    var checkPwErrorType by mutableStateOf(InputErrorType.NONE)

    fun onIdChanged(input: String) {
        userId = input
        idErrorType = if (checkInputRegex(InputRegexType.ID, input)) InputErrorType.NONE else InputErrorType.ID_REGEX
    }

    fun onPwChanged(input: String) {
        password = input
        pwErrorType = if (checkInputRegex(InputRegexType.PASSWORD, input)) InputErrorType.NONE else InputErrorType.PW_REGEX
        checkPwErrorType = if (password == checkPassword) InputErrorType.NONE else InputErrorType.NOT_SAME_PW
    }

    fun onCheckPwChanged(input: String) {
        checkPassword = input
        checkPwErrorType = if (password == checkPassword) InputErrorType.NONE else InputErrorType.NOT_SAME_PW
    }

    fun onSignUpClick() {
        viewModelScope.launch {
            isLoading = true
            val result = repository.signUp(userId, password)
            isLoading = false
            result.onSuccess {
                isSignUpSuccess = true
            }.onFailure {
                if (it.message == "409") {
                    idErrorType = InputErrorType.OVERLAP_ID
                } else {
                    isSignUpSuccess = false
                }
            }
        }
    }
}