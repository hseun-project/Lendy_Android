package com.hseun.lendy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hseun.lendy.R
import com.hseun.lendy.ui.theme.Gray300
import com.hseun.lendy.ui.theme.Gray400
import com.hseun.lendy.ui.theme.LendyFontStyle
import com.hseun.lendy.ui.theme.Main
import com.hseun.lendy.ui.theme.Red
import com.hseun.lendy.ui.theme.White
import com.hseun.lendy.ui.utils.singleClickEvent
import com.hseun.lendy.utils.InputErrorType
import com.hseun.lendy.utils.inputErrorMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LendyTopBar(
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .drawBehind {
                drawLine(
                    color = Gray400,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 0.4.dp.toPx()
                )
            },
        title = {
            Image(
                painter = painterResource(id = R.drawable.lendy_logo_header),
                contentDescription = "Lendy Logo in Header"
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White
        )
    )
}

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    errorType: InputErrorType
) {
    Text(
        modifier = modifier
            .padding(
                start = 32.dp,
                end = 32.dp,
                top = 2.dp
            )
            .fillMaxWidth(),
        text = stringResource(inputErrorMessage(errorType)),
        style = LendyFontStyle.medium12,
        color = Red
    )
}

@Composable
fun LendyButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    text: String,
    onClick: () -> Unit
) {
    singleClickEvent { singleEvent ->
        Button(
            modifier = modifier
                .padding(
                    start = 30.dp,
                    end = 30.dp
                )
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                singleEvent.event {
                    onClick()
                }
            },
            colors = ButtonColors(
                disabledContentColor = White,
                disabledContainerColor = Gray300,
                contentColor = White,
                containerColor = Main
            ),
            enabled = enabled
        ) {
            Text(
                text = text,
                style = LendyFontStyle.semibold20,
                color = White
            )
        }
    }
}