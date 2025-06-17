package com.hseun.lendy.mypage.data

data class MyPageResponse(
    val userId: String,
    val name: String,
    val creditScore: Int,
    val banks: List<BankData>
)

data class BankData(
    val bankName: String,
    val bankNumber: String
)