package com.hseun.lendy.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hseun.lendy.R
import com.hseun.lendy.mypage.data.BankData
import com.hseun.lendy.mypage.data.MyApplyLoanListItemData
import com.hseun.lendy.mypage.viewmodel.MyPageViewModel
import com.hseun.lendy.ui.LendyAlertDialog
import com.hseun.lendy.ui.LendyButton
import com.hseun.lendy.ui.list.MyApplyLoanListItem
import com.hseun.lendy.ui.theme.Gray600
import com.hseun.lendy.ui.theme.LendyFontStyle
import com.hseun.lendy.ui.theme.White
import com.hseun.lendy.ui.utils.dropShadow
import com.hseun.lendy.ui.utils.noRippleClickable

const val NAVIGATION_MY_PAGE = "myPage"

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    navToAuth: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val userId = viewModel.userId
    val name = viewModel.name
    val creditScore = viewModel.creditScore
    val bankList = viewModel.bankList
    val myApplyLoanList = viewModel.myApplyLoanList

    val isLoading = viewModel.isLoading
    val isLogoutSuccess = viewModel.isLogoutSuccess

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        if (!isLoading) {
            viewModel.getInfo()
        }
    }

    Column (
        modifier = modifier
            .fillMaxWidth()
            .background(White)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = modifier.height(30.dp))
        MyInfo(
            name = name,
            userId = userId,
            score = creditScore
        )
        Spacer(modifier = modifier.height(20.dp))
        BankList(bankList = bankList)
        Spacer(modifier = modifier.height(20.dp))
        MyApplyLoan(myApplyLoanList = myApplyLoanList)
        Spacer(modifier = modifier.height(40.dp))
        LendyButton(
            enabled = !isLoading,
            text = "로그아웃",
            onClick = {
                viewModel.logout()
            }
        )
    }

    if (isLogoutSuccess == false) {
        LendyAlertDialog(
            title = "로그아웃에 실패하였습니다",
            onClick = {
                viewModel.onChangeLogoutSuccess()
            }
        )
    }
    if (isLogoutSuccess == true) {
        navToAuth()
    }
}

@Composable
private fun MyInfo(
    modifier: Modifier = Modifier,
    name: String,
    userId: String,
    score: Int
) {
    Row (
        modifier = modifier
            .padding(
                start = 30.dp,
                end = 30.dp
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row {
                Text(
                    text = name,
                    style = LendyFontStyle.semibold20
                )
                Text(
                    text = "님",
                    style = LendyFontStyle.semibold16
                )
            }
            Text(
                text = userId,
                style = LendyFontStyle.medium14,
                color = Gray600
            )
        }
        Column {
            Text(
                text = "신용점수",
                style = LendyFontStyle.medium13,
                color = Gray600
            )
            Text(
                text = "${score}점",
                style = LendyFontStyle.semibold21
            )
        }
    }
}

@Composable
private fun BankListItem(
    modifier: Modifier = Modifier,
    bankName: String,
    bankNumber: String
) {
    Row {
        Text(
            text = bankName,
            style = LendyFontStyle.medium14
        )
        Spacer(modifier = modifier.width(12.dp))
        Text(
            text = bankNumber,
            style = LendyFontStyle.medium14
        )
    }
}

@Composable
private fun BankList(
    modifier: Modifier = Modifier,
    bankList: List<BankData>
) {
    Column (
        modifier = modifier
            .padding(
                start = 30.dp,
                end = 30.dp
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 20.dp,
                end = 20.dp
            )
    ) {
        Text(
            text = "계좌 정보",
            style = LendyFontStyle.semibold16
        )
        bankList.forEach { bank ->
            BankListItem(
                bankName = bank.bankName,
                bankNumber = bank.bankNumber
            )
        }
    }
}

@Composable
fun MyApplyLoan(
    modifier: Modifier = Modifier,
    myApplyLoanList: List<MyApplyLoanListItemData>
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
            .padding(
                start = 30.dp,
                end = 30.dp
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .dropShadow()
            .background(
                color = White,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            )
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth()
                .noRippleClickable {
                    isExpanded = !isExpanded
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "대출 요청 내역",
                style = LendyFontStyle.semibold15
            )
            Image(
                modifier = modifier
                    .width(12.dp)
                    .rotate(if (isExpanded) 180f else 0f),
                painter = painterResource(id = R.drawable.icon_arrow),
                contentDescription = "펼쳐보기"
            )
        }
        if (isExpanded) {
            myApplyLoanList.forEach { data ->
                MyApplyLoanListItem(data = data)
            }
        }
    }
}