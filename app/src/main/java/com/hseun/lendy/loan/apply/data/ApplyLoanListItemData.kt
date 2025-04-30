package com.hseun.lendy.loan.apply.data

import com.hseun.lendy.utils.DuringType

data class ApplyLoanListItemData(
    val applyLoansId: Long,
    val debtName: String,
    val creditScore: Int,
    val money: Int,
    val duringType: DuringType,
    val during: Int
)