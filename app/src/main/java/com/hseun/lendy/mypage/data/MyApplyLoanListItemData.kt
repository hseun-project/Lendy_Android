package com.hseun.lendy.mypage.data

import com.hseun.lendy.utils.ApplyLoan
import com.hseun.lendy.utils.ApplyState
import com.hseun.lendy.utils.DuringType

data class MyApplyLoanListItemData(
    val applyLoanId: Long,
    val loanType: ApplyLoan,
    val money: Int,
    val interest: Float,
    val duringType: DuringType,
    val during: Int,
    val bondName: String,
    val bondMail: String,
    val state: ApplyState
)