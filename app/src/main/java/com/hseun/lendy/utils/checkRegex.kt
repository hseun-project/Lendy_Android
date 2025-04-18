package com.hseun.lendy.utils

enum class InputRegexType {
    ID,
    PASSWORD
}

fun checkInputRegex(
    type: InputRegexType,
    input: String
): Boolean {
    val regex = when (type) {
        InputRegexType.ID -> "^[a-zA-Z0-9]{4,20}$"
        InputRegexType.PASSWORD -> "^[a-zA-Z0-9~!@#$%^&*()_+=?\\.-]{8,20}$"
    }.toRegex()
    return regex.matches(input)
}