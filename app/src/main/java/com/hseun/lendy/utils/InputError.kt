package com.hseun.lendy.utils

import com.hseun.lendy.R

enum class InputErrorType {
    NONE,
    WRONG_ID_OR_PW,

    ID_REGEX,
    PW_REGEX,
    NOT_SAME_PW,
    OVERLAP_ID,

    OVER_MAX_PRICE,
    OVER_INTEREST
}

fun inputErrorMessage(errorType: InputErrorType): Int {
    return when(errorType) {
        InputErrorType.NONE -> R.string.error_none
        InputErrorType.WRONG_ID_OR_PW -> R.string.error_wrong_id_or_pw

        InputErrorType.ID_REGEX -> R.string.error_id_regex
        InputErrorType.PW_REGEX -> R.string.error_pw_regex
        InputErrorType.NOT_SAME_PW -> R.string.error_not_same_pw
        InputErrorType.OVERLAP_ID -> R.string.error_overlap_id

        InputErrorType.OVER_MAX_PRICE -> R.string.error_over_max_price
        InputErrorType.OVER_INTEREST -> R.string.error_over_interest
    }
}