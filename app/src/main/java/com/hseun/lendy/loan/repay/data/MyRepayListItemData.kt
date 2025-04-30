package com.hseun.lendy.loan.repay.data

import com.hseun.lendy.utils.DuringType
import java.util.Date

data class MyRepayListItemData(
    val loanId: Long,
    val money: Int,
    val duringType: DuringType,
    val during: Int,
    val startDate: Date,
    val repayment: Int
)