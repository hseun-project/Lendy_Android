package com.hseun.lendy.ui.list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.hseun.lendy.mypage.data.MyApplyLoanListItemData
import com.hseun.lendy.ui.theme.Gray600
import com.hseun.lendy.ui.theme.LendyFontStyle
import com.hseun.lendy.ui.theme.White
import com.hseun.lendy.ui.utils.dropShadow
import com.hseun.lendy.utils.ApplyLoan
import com.hseun.lendy.utils.ApplyState

@SuppressLint("DefaultLocale")
@Composable
fun MyApplyLoanListItem(
    modifier: Modifier = Modifier,
    data: MyApplyLoanListItemData
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow(
                blur = 4.dp
            )
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                top = 10.dp,
                bottom = 10.dp
            )
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MyApplyItemState(
            loanType = data.loanType,
            state = data.state
        )
        if (data.loanType == ApplyLoan.PRIVATE_LOAN) {
            MyApplyItemBond(
                name = data.bondName,
                mail = data.bondMail
            )
        }
        MyApplyItemText(
            label = "요청 금액",
            value = "${String.format("%,d", data.money)}원"
        )
        MyApplyItemText(
            label = "이자율",
            value = "${data.interest}%"
        )
        MyApplyItemText(
            label = "상환기한",
            value = "${data.during}${data.duringType.displayText}"
        )
    }
}

@Composable
private fun MyApplyItemState(
    modifier: Modifier = Modifier,
    loanType: ApplyLoan,
    state: ApplyState
) {
    Row (
        modifier = modifier
            .padding(
                start = 12.dp,
                end = 16.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = modifier
                .wrapContentSize()
                .background(loanType.displayBackgroundColor)
                .padding(
                    start = 7.dp,
                    end = 7.dp,
                    top = 3.dp,
                    bottom = 3.dp
                ),
            text = loanType.displayText,
            style = LendyFontStyle.semibold11,
            color = loanType.displayTextColor
        )
        Text(
            text = state.displayText,
            style = LendyFontStyle.semibold12,
            color = state.displayTextColor
        )
    }
}

@Composable
private fun MyApplyItemText(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
     Row (
         modifier = modifier
             .padding(
                 start = 16.dp,
                 bottom = 16.dp
             )
             .fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween,
         verticalAlignment = Alignment.CenterVertically
     ) {
         Text(
             text = label,
             style = LendyFontStyle.medium13
         )
         Text(
             text = value,
             style = LendyFontStyle.medium13
         )
     }
}

@Composable
private fun MyApplyItemBond(
    modifier: Modifier = Modifier,
    name: String,
    mail: String
) {
    Row (
        modifier = modifier
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "요청 대상",
            style = LendyFontStyle.medium13
        )
        Row (
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = name,
                style = LendyFontStyle.medium13
            )
            Text(
                text = "(${mail})",
                style = LendyFontStyle.medium10,
                color = Gray600
            )
        }
    }
}