package com.hseun.lendy.utils

enum class DuringType {
    DAY,
    MONTH;

    val displayText: String
        get() = when(this) {
            DAY -> "일"
            MONTH -> "개월"
        }
}

enum class ApplyLoan {
    PRIVATE_LOAN,
    PUBLIC_LOAN
}