package com.hseun.lendy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.hseun.lendy.auth.AuthActivity
import com.hseun.lendy.navigation.BottomNavigation
import com.hseun.lendy.navigation.MainNavigation
import com.hseun.lendy.ui.LendyTopBar
import com.hseun.lendy.ui.theme.LendyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LendyTheme {
                LendyScreen(
                    navToAuth = {
                        val intent = Intent(this, AuthActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun LendyScreen(
    modifier: Modifier = Modifier,
    navToAuth: () -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { LendyTopBar() },
        bottomBar = { BottomNavigation(navController = navController)}
    ) {
        Box(modifier = modifier.padding(it)) {
            MainNavigation(
                navController = navController,
                navToAuth = navToAuth
            )
        }
    }
}