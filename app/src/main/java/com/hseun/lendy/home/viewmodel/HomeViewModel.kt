package com.hseun.lendy.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hseun.lendy.home.HomeRepository
import com.hseun.lendy.loan.apply.data.ApplyLoanListItemData
import com.hseun.lendy.loan.repay.data.LentRepayListItemData
import com.hseun.lendy.loan.repay.data.MyRepayListItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    var name by mutableStateOf("")
    var creditScore by mutableIntStateOf(0)
    var applyLoanList: List<ApplyLoanListItemData> by mutableStateOf(listOf())
    var myRepayList: List<MyRepayListItemData> by mutableStateOf(listOf())
    var lentRepayList: List<LentRepayListItemData> by mutableStateOf(listOf())

    var isLoading by mutableStateOf(false)

    fun getInfo() {
        viewModelScope.launch {
            isLoading = true
            val creditResult = repository.getCredit()
            creditResult.onSuccess { response ->
                creditScore = response.creditScore
                name = response.name
            }
            val applyResult = repository.getApplyLoanRequest()
            applyResult.onSuccess { response ->
                applyLoanList = response
            }
            val myRepayResult = repository.getMyRepay()
            myRepayResult.onSuccess { response ->
                myRepayList = response
            }
            val lentRepayResult = repository.getLentRepay()
            lentRepayResult.onSuccess { response ->
                lentRepayList = response
            }
            isLoading = false
        }
    }
}