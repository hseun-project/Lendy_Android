package com.hseun.lendy.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hseun.lendy.home.viewmodel.HomeViewModel
import com.hseun.lendy.loan.apply.data.ApplyLoanListItemData
import com.hseun.lendy.loan.repay.data.LentRepayListItemData
import com.hseun.lendy.loan.repay.data.MyRepayListItemData
import com.hseun.lendy.ui.LendyButton
import com.hseun.lendy.ui.list.ApplyLoanListItem
import com.hseun.lendy.ui.list.LentRepayListItem
import com.hseun.lendy.ui.list.MyRepayListItem
import com.hseun.lendy.ui.theme.BackgroundColor
import com.hseun.lendy.ui.theme.Gray200
import com.hseun.lendy.ui.theme.Gray600
import com.hseun.lendy.ui.theme.LendyFontStyle
import com.hseun.lendy.ui.theme.Main300
import com.hseun.lendy.ui.theme.Main50
import com.hseun.lendy.ui.theme.White
import com.hseun.lendy.utils.DuringType
import java.util.Date

const val NAVIGATION_HOME = "home"

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navToApplyLoan: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val name = viewModel.name
    val creditScore = viewModel.creditScore
    val applyLoanList = viewModel.applyLoanList
    val myRepayList = viewModel.myRepayList
    val lentRepayList = viewModel.lentRepayList

    val isLoading = viewModel.isLoading

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        if (!isLoading) {
            viewModel.getInfo()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(scrollState)
    ) {
        MyInfo(
            name = name,
            creditScore = creditScore,
            navToApplyLoan = navToApplyLoan
        )
        if (applyLoanList.isNotEmpty()) {
            ApplyLoanList(dataList = applyLoanList, navController = navController)
        }
        if (myRepayList.isNotEmpty()) {
            MyRepayList(dataList = myRepayList, navController = navController)
        }
        if (lentRepayList.isNotEmpty()) {
            LentRepayList(dataList = lentRepayList, navController = navController)
        }
    }
}

@Composable
private fun MyInfo(
    modifier: Modifier = Modifier,
    name: String,
    creditScore: Int,
    navToApplyLoan: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(White)
            .padding(
                start = 30.dp,
                end = 30.dp,
                top = 20.dp,
                bottom = 20.dp
            )
    ) {
        Text(
            text = "안녕하세요",
            style = LendyFontStyle.medium12,
            color = Gray600
        )
        Text(
            modifier = modifier
                .padding(
                    top = 2.dp,
                    bottom = 8.dp
                ),
            text = "${name}님",
            style = LendyFontStyle.semibold20
        )
        MyCreditScore(creditScore = creditScore)
        LendyButton(
            enabled = true,
            text = "대출 신청",
            onClick = navToApplyLoan
        )
    }
}

@Composable
private fun MyCreditScore(
    modifier: Modifier = Modifier,
    creditScore: Int
) {
    Column(
        modifier = modifier
            .padding(
                bottom = 20.dp
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = Main300,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 12.dp
            )
    ) {
        Text(
            text = "나의 신용점수",
            style = LendyFontStyle.semibold13,
            color = White
        )
        Row(
            modifier = modifier
                .padding(
                    top = 6.dp,
                    bottom = 4.dp
                )
        ) {
            Text(
                text = "$creditScore",
                style = LendyFontStyle.semibold24,
                color = White
            )
            Text(
                text = " / 1000",
                style = LendyFontStyle.medium14,
                color = Gray200
            )
        }
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(10.dp),
            progress = { creditScore.toFloat() / 1000 },
            trackColor = Main50,
            color = White,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
fun ApplyLoanList(
    dataList: List<ApplyLoanListItemData>,
    navController: NavHostController
) {
    ListBased(
        title = "대출 요청",
        dataList = dataList,
        navController = navController,
        routerBuilder = { data -> "applyDetail/${data.applyLoansId}" },
        listItemComponent = { data, onClick ->
            ApplyLoanListItem(data = data, onButtonClick = onClick)
        }
    )
}

@Composable
fun MyRepayList(
    dataList: List<MyRepayListItemData>,
    navController: NavHostController
) {
    ListBased(
        title = "대출 상환 현황",
        dataList = dataList,
        navController = navController,
        routerBuilder = { data -> "myRepayDetail/${data.loanId}" },
        listItemComponent = { data, onClick ->
            MyRepayListItem(data = data, onClick = onClick)
        }
    )
}

@Composable
fun LentRepayList(
    dataList: List<LentRepayListItemData>,
    navController: NavHostController
) {
    ListBased(
        title = "대출 현황",
        dataList = dataList,
        navController = navController,
        routerBuilder = { data -> "lentRepayDetail/${data.loanId}" },
        listItemComponent = { data, onClick ->
            LentRepayListItem(data = data, onClick = onClick)
        }
    )
}

@Composable
private fun <T> ListBased(
    modifier: Modifier = Modifier,
    title: String,
    dataList: List<T>,
    navController: NavHostController,
    routerBuilder: (T) -> String,
    listItemComponent: @Composable (T, () -> Unit) -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                start = 30.dp,
                end = 30.dp,
                bottom = 30.dp
            )
            .fillMaxWidth()
    ) {
        Spacer(modifier = modifier.height(40.dp))
        Text(
            text = title,
            style = LendyFontStyle.bold18
        )
        Column(
            modifier = modifier
                .padding(
                    top = 8.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (data in dataList) {
                listItemComponent(data) { navController.navigate(routerBuilder(data)) }
            }
        }
    }
}