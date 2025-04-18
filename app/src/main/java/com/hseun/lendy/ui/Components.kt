package com.hseun.lendy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hseun.lendy.R
import com.hseun.lendy.ui.theme.Gray300
import com.hseun.lendy.ui.theme.Gray400
import com.hseun.lendy.ui.theme.LendyFontStyle
import com.hseun.lendy.ui.theme.Main
import com.hseun.lendy.ui.theme.White
import com.hseun.lendy.ui.utils.noRippleClickable

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
fun LendyButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(
                start = 30.dp,
                end = 30.dp
            )
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
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

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    buttonText: String,
    isNotText: String,
    moveToWhereText: String,
    onButtonClick: () -> Unit,
    onTextClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                bottom = 70.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LendyButton(
            enabled = enabled,
            text = buttonText,
            onClick = onButtonClick
        )
        Row(
            modifier = modifier
                .padding(
                    top = 16.dp
                )
        ) {
            Text(
                text = isNotText,
                style = LendyFontStyle.medium15
            )
            Text(
                modifier = modifier
                    .padding(
                        start = 6.dp
                    )
                    .noRippleClickable {
                        onTextClick()
                    },
                text = moveToWhereText,
                style = LendyFontStyle.bold15,
                color = Main
            )
        }
    }
}

@Composable
fun AuthLogo(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(80.dp))
    Image(
        modifier = modifier
            .fillMaxWidth(0.45f)
            .wrapContentHeight(),
        painter = painterResource(id = R.drawable.lendy_logo_auth),
        contentDescription = "Lendy Logo",
        contentScale = ContentScale.FillWidth
    )
    Spacer(modifier = modifier.height(52.dp))
}