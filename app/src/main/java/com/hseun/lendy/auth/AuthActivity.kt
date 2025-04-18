package com.hseun.lendy.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hseun.lendy.MainActivity
import com.hseun.lendy.navigation.AuthNavigation
import com.hseun.lendy.ui.theme.LendyTheme
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LendyTheme {
                AuthNavigation(
                    navToMain = {
                        Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }.let { intent ->
                            startActivity(intent)
                        }
                    }
                )
            }
        }
    }
}