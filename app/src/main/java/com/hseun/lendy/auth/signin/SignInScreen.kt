package com.hseun.lendy.auth.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hseun.lendy.R
import com.hseun.lendy.auth.signin.viewmodel.SignInViewModel
import com.hseun.lendy.ui.AuthButton
import com.hseun.lendy.ui.AuthLogo
import com.hseun.lendy.ui.LendyInput
import com.hseun.lendy.ui.LendyPasswordInput
import com.hseun.lendy.ui.theme.White
import com.hseun.lendy.ui.utils.addFocusCleaner
import com.hseun.lendy.utils.InputErrorType

const val NAVIGATION_LOGIN = "signIn"

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
    navToMain: () -> Unit,
    navToSignUp: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val userId = viewModel.userId
    val password = viewModel.password

    val isLoading = viewModel.isLoading
    val isSignInSuccess = viewModel.isSignInSuccess
    val buttonEnabled by remember(isLoading, userId, password) {
        derivedStateOf {
            !isLoading &&
            userId.isNotEmpty() &&
            password.isNotEmpty()
        }
    }

    if (isSignInSuccess == true) {
        navToMain()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .addFocusCleaner(focusManager)
    ) {
        Column(
            modifier = modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AuthLogo()
            LendyInput(
                label = stringResource(id = R.string.auth_id),
                input = userId, 
                hint = stringResource(id = R.string.auth_id), 
                imeAction = ImeAction.Next, 
                errorType = InputErrorType.NONE,
                onValueChange = { input ->
                    viewModel.onIdChanged(input)
                }
            )
            LendyPasswordInput(
                label = stringResource(id = R.string.auth_pw), 
                input = password,
                hint = stringResource(id = R.string.auth_pw), 
                imeAction = ImeAction.Done, 
                errorType = if (isSignInSuccess == false) InputErrorType.WRONG_ID_OR_PW else InputErrorType.NONE,
                onValueChange = { input ->
                    viewModel.onPasswordChanged(input)
                }
            )
        }
        AuthButton(
            modifier = modifier.align(Alignment.BottomCenter),
            enabled = buttonEnabled,
            buttonText = stringResource(id = R.string.login),
            isNotText = stringResource(id = R.string.login_is_not_member),
            moveToWhereText = stringResource(id = R.string.login_go_signup),
            onButtonClick = {
                viewModel.onSignInClick()
            },
            onTextClick = {
                navToSignUp()
            }
        )
    }
}