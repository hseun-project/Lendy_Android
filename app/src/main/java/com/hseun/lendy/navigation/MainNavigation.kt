package com.hseun.lendy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hseun.lendy.home.HomeScreen
import com.hseun.lendy.mypage.MyPageScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    navToAuth: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.screenRoute
    ) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreen(
                navController = navController,
                navToApplyLoan = {}
            )
        }
        composable(BottomNavItem.OpenLoan.screenRoute) {
            // 공개 대출 화면
        }
        composable(BottomNavItem.MyPage.screenRoute) {
            MyPageScreen(
                navToAuth = navToAuth
            )
        }
    }
}