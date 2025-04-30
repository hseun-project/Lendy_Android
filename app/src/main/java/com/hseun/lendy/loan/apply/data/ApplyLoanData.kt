package com.hseun.lendy.loan.apply.data

import com.hseun.lendy.utils.DuringType

data class ApplyLoanData(
    val applyLoanId: Long,
    val debtName: String,
    val money: Int,
    val interest: Float,
    val duringType: DuringType,
    val during: Int
)
