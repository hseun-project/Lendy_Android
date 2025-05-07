package com.hseun.lendy.mypage.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy.mypage.MyPageRepository
import com.hseun.lendy.mypage.data.BankData
import com.hseun.lendy.mypage.data.MyApplyLoanListItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val repository: MyPageRepository
) : ViewModel() {
    var userId by mutableStateOf("")
    var name by mutableStateOf("")
    var creditScore by mutableIntStateOf(0)
    var bankList: List<BankData> by mutableStateOf(listOf())
    var myApplyLoanList: List<MyApplyLoanListItemData> by mutableStateOf(listOf())

    var isLoading by mutableStateOf(false)
    var isLogoutSuccess by mutableStateOf<Boolean?>(null)

    fun getInfo() {
        viewModelScope.launch {
            isLoading = true
            val myInfoResult = async { repository.getMyInfo() }
            val myApplyLoanResult = async { repository.getMyApplyLoan() }

            myInfoResult.await().onSuccess { response ->
                userId = response.userId
                name = response.name
                creditScore = response.creditScore
                bankList = response.banks
            }
            myApplyLoanResult.await().onSuccess { response ->
                myApplyLoanList = response
            }
            isLoading = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            isLoading = true
            val result = repository.logout()
            result.onSuccess {
                isLoading = false
                isLogoutSuccess = true
            }.onFailure {
                isLoading = false
                isLogoutSuccess = false
            }
        }
    }

    fun onChangeLogoutSuccess() {
        isLogoutSuccess = null
    }
}