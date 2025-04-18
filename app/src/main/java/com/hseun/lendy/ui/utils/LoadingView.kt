package com.hseun.lendy.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hseun.lendy.ui.theme.Gray500
import com.hseun.lendy.ui.theme.LendyFontStyle
import com.hseun.lendy.ui.theme.Main

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = modifier
                .size(60.dp),
            color = Main
        )
        Spacer(modifier = modifier.height(12.dp))
        Text(
            text = "본인인증 화면을 불러오는 중입니다",
            style = LendyFontStyle.bold15,
            color = Gray500
        )
    }
}