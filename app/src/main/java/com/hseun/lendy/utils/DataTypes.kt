package com.hseun.lendy.utils

import androidx.compose.ui.graphics.Color
import com.hseun.lendy.ui.theme.Blue
import com.hseun.lendy.ui.theme.Green
import com.hseun.lendy.ui.theme.Main
import com.hseun.lendy.ui.theme.Purple
import com.hseun.lendy.ui.theme.Red
import com.hseun.lendy.ui.theme.TagBlue
import com.hseun.lendy.ui.theme.TagPurple

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
    PUBLIC_LOAN;

    val displayText: String
        get() = when(this) {
            PRIVATE_LOAN -> "개인"
            PUBLIC_LOAN -> "공개"
        }
    val displayTextColor: Color
        get() = when(this) {
            PRIVATE_LOAN -> Purple
            PUBLIC_LOAN -> Main
        }
    val displayBackgroundColor: Color
        get() = when(this) {
            PRIVATE_LOAN -> TagPurple
            PUBLIC_LOAN -> TagBlue
        }
}

enum class ApplyState {
    APPROVAL,
    PENDING,
    REJECTED;

    val displayText: String
        get() = when(this) {
            APPROVAL -> "승인"
            PENDING -> "대기"
            REJECTED -> "거절"
        }
    val displayTextColor: Color
        get() = when(this) {
            APPROVAL -> Green
            PENDING -> Blue
            REJECTED -> Red
        }
}