package com.hseun.lendy.ui.list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.hseun.lendy.loan.repay.data.LentRepayListItemData
import com.hseun.lendy.ui.theme.Gray100
import com.hseun.lendy.ui.theme.Gray500
import com.hseun.lendy.ui.theme.LendyFontStyle
import com.hseun.lendy.ui.theme.Main
import com.hseun.lendy.ui.theme.White
import com.hseun.lendy.ui.utils.dropShadow
import com.hseun.lendy.ui.utils.noRippleClickable
import com.hseun.lendy.utils.DuringType
import com.hseun.lendy.utils.calculateEndDate
import java.util.Date

@Composable
fun LentRepayListItem(
    modifier: Modifier = Modifier,
    data: LentRepayListItemData,
    onClick: () -> Unit
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .noRippleClickable { onClick() }
            .padding(
                top = 12.dp,
                bottom = 12.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        LentRepayItemText(
            name = data.debtName,
            money = data.money,
            duringType = data.duringType,
            during = data.during,
            startDate = data.startDate
        )
        Spacer(modifier = modifier.height(8.dp))
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(10.dp),
            progress = { (data.money - data.repayment).toFloat() / data.money },
            trackColor = Gray100,
            color = Main,
            strokeCap = StrokeCap.Round
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun LentRepayItemText(
    modifier: Modifier = Modifier,
    name: String,
    money: Int,
    duringType: DuringType,
    during: Int,
    startDate: Date
) {
    Box (
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column (
            modifier = modifier.align(Alignment.TopStart)
        ) {
            Text(
                text = name,
                style = LendyFontStyle.semibold16
            )
            Text(
                modifier = modifier.padding(top = 2.dp),
                text = "상환일: ${calculateEndDate(startDate, duringType, during)}",
                style = LendyFontStyle.medium12,
                color = Gray500
            )
        }
        Text(
            modifier = modifier
                .align(Alignment.CenterEnd),
            text = "₩ ${String.format("%,d", money)}",
            style = LendyFontStyle.semibold16
        )
    }
}