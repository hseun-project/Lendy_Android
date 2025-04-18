package com.hseun.lendy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hseun.lendy.auth.signin.NAVIGATION_LOGIN
import com.hseun.lendy.auth.signin.SignInScreen
import com.hseun.lendy.auth.signup.NAVIGATION_SIGNUP
import com.hseun.lendy.auth.signup.SignUpScreen
import com.hseun.lendy.auth.splash.LendySplashScreen
import com.hseun.lendy.auth.splash.NAVIGATION_SPLASH
import com.hseun.lendy.auth.verification.VerificationScreen
import com.hseun.lendy.auth.verification.NAVIGATION_VERIFICATION

@Composable
fun AuthNavigation(
    navController: NavHostController = rememberNavController(),
    navToMain: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NAVIGATION_SPLASH
    ) {
        composable(NAVIGATION_SPLASH) {
            LendySplashScreen(
                navToMain = navToMain,
                navToSignIn = {
                    navController.navigate(NAVIGATION_LOGIN) {
                        popUpTo(NAVIGATION_SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(NAVIGATION_SIGNUP) {
            SignUpScreen(
                onSuccess = { userId ->
                    navController.navigate("${NAVIGATION_VERIFICATION}/${userId}")
                },
                navToSignIn = {
                    navController.navigate(NAVIGATION_LOGIN) {
                        popUpTo(NAVIGATION_SIGNUP) { inclusive = true }
                    }
                }
            )
        }

        composable(
            "${NAVIGATION_VERIFICATION}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            VerificationScreen(
                userId = userId,
                navigationGoBack = {
                    navController.popBackStack()
                },
                navToSignIn = {
                    navController.navigate(NAVIGATION_LOGIN) {
                        popUpTo("${NAVIGATION_VERIFICATION}/{userId}") { inclusive = true }
                    }
                }
            )
        }

        composable(NAVIGATION_LOGIN) {
            SignInScreen(
                navToMain = navToMain,
                navToSignUp = {
                    navController.navigate(NAVIGATION_SIGNUP) {
                        popUpTo(NAVIGATION_LOGIN) { inclusive = true }
                    }
                }
            )
        }
    }
}