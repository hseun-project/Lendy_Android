package com.hseun.lendy.auth.signup

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
import com.hseun.lendy.auth.signup.viewmodel.SignUpViewModel
import com.hseun.lendy.ui.AuthButton
import com.hseun.lendy.ui.AuthLogo
import com.hseun.lendy.ui.LendyInput
import com.hseun.lendy.ui.LendyPasswordInput
import com.hseun.lendy.ui.theme.White
import com.hseun.lendy.ui.utils.addFocusCleaner
import com.hseun.lendy.utils.InputErrorType

const val NAVIGATION_SIGNUP = "signUp"

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    onSuccess: (String) -> Unit,
    navToSignIn: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    val userId = viewModel.userId
    val password = viewModel.password
    val checkPassword = viewModel.checkPassword

    val idErrorType = viewModel.idErrorType
    val pwErrorType = viewModel.pwErrorType
    val checkPwErrorType = viewModel.checkPwErrorType

    val isLoading = viewModel.isLoading
    val isSignUpSuccess = viewModel.isSignUpSuccess
    val buttonEnabled by remember(isLoading, idErrorType, pwErrorType, checkPwErrorType) {
        derivedStateOf {
            !isLoading &&
            userId.isNotEmpty() &&
            password.isNotEmpty() &&
            idErrorType == InputErrorType.NONE &&
            pwErrorType == InputErrorType.NONE &&
            checkPwErrorType == InputErrorType.NONE
        }
    }

    if (isSignUpSuccess) {
        onSuccess(userId)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .addFocusCleaner(focusManager)
    ) {
        Column(
            modifier = modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthLogo()
            LendyInput(
                label = stringResource(id = R.string.auth_id),
                input = userId,
                hint = stringResource(id = R.string.auth_id),
                imeAction = ImeAction.Next,
                errorType = idErrorType,
                onValueChange = { input ->
                    viewModel.onIdChanged(input)
                }
            )
            LendyPasswordInput(
                label = stringResource(id = R.string.auth_pw),
                input = password,
                hint = stringResource(id = R.string.auth_pw),
                imeAction = ImeAction.Next,
                errorType = pwErrorType,
                onValueChange = { input ->
                    viewModel.onPwChanged(input)
                }
            )
            LendyPasswordInput(
                label = stringResource(id = R.string.auth_pw_check),
                input = checkPassword,
                hint = stringResource(id = R.string.auth_pw_check),
                imeAction = ImeAction.Done,
                errorType = checkPwErrorType,
                onValueChange = { input ->
                    viewModel.onCheckPwChanged(input)
                }
            )
        }
        AuthButton(
            modifier = modifier.align(Alignment.BottomCenter),
            enabled = buttonEnabled,
            buttonText = stringResource(id = R.string.signup),
            isNotText = stringResource(id = R.string.signup_is_member),
            moveToWhereText = stringResource(id = R.string.signup_go_login),
            onButtonClick = {
                viewModel.onSignUpClick()
            },
            onTextClick = navToSignIn
        )
    }
}