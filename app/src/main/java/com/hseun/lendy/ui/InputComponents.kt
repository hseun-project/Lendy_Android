package com.hseun.lendy.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.hseun.lendy.R
import com.hseun.lendy.ui.theme.Gray400
import com.hseun.lendy.ui.theme.Gray500
import com.hseun.lendy.ui.theme.LendyFontStyle
import com.hseun.lendy.ui.theme.Main
import com.hseun.lendy.ui.theme.Red
import com.hseun.lendy.utils.InputErrorType
import com.hseun.lendy.utils.inputErrorMessage

@Composable
private fun LendyTextField(
    modifier: Modifier = Modifier,
    input: String,
    hint: String,
    imeAction: ImeAction,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .drawBehind {
                drawLine(
                    color = if (isFocused) Main else Gray500,
                    strokeWidth = 1.dp.toPx(),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height)
                )
            }
            .padding(
                start = 4.dp,
                end = 4.dp,
                top = 12.dp,
                bottom = 12.dp
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
    ) {
        BasicTextField(
            modifier = modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(),
            value = input,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(true)
                },
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            textStyle = LendyFontStyle.medium15,
            cursorBrush = SolidColor(Main),
            decorationBox = { innerTextField ->
                if (input.isEmpty()) {
                    Text(
                        text = hint,
                        style = LendyFontStyle.medium15,
                        color = Gray400
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun LendyPasswordTextField(
    modifier: Modifier = Modifier,
    input: String,
    hint: String,
    imeAction: ImeAction,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var isShowPassword by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .drawBehind {
                drawLine(
                    color = if (isFocused) Main else Gray500,
                    strokeWidth = 1.dp.toPx(),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height)
                )
            }
            .padding(
                start = 4.dp,
                end = 6.dp,
                top = 11.dp,
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
    ) {
        BasicTextField(
            modifier = modifier
                .align(Alignment.CenterStart)
                .padding(
                    end = 24.dp
                )
                .fillMaxWidth(),
            value = input,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus(true)
                },
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            ),
            visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = LendyFontStyle.medium15,
            cursorBrush = SolidColor(Main),
            decorationBox = { innerTextField ->
                if (input.isEmpty()) {
                    Text(
                        text = hint,
                        style = LendyFontStyle.medium15,
                        color = Gray400
                    )
                }
                innerTextField()
            }
        )
        IconButton(
            modifier = modifier
                .align(Alignment.CenterEnd),
            onClick = {
                isShowPassword = !isShowPassword
            }
        ) {
            val passwordIcon =
                if (isShowPassword) R.drawable.icon_password_show else R.drawable.icon_password_hide
            val contentDescription =
                if (isShowPassword) "Hide password Icon" else "Show password Icon"

            Icon(
                modifier = modifier.size(20.dp),
                painter = painterResource(id = passwordIcon),
                contentDescription = contentDescription
            )
        }
    }
}

@Composable
private fun LendyNumberTextField(
    modifier: Modifier = Modifier,
    input: String,
    imeAction: ImeAction,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .drawBehind {
                drawLine(
                    color = if (isFocused) Main else Gray500,
                    strokeWidth = 1.dp.toPx(),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height)
                )
            }
            .padding(
                start = 8.dp,
                top = 8.dp,
                bottom = 8.dp
            )
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
    ) {
        BasicTextField(
            modifier = modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(),
            value = input,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                },
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            textStyle = LendyFontStyle.medium17,
            cursorBrush = SolidColor(Main),
            decorationBox = { innerTextField ->
                if (input.isEmpty()) {
                    Text(
                        text = "0",
                        style = LendyFontStyle.medium17,
                        color = Gray400
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun InputLabel(
    label: String
) {
    Text(
        text = label,
        style = LendyFontStyle.semibold14
    )
}

@Composable
private fun ErrorMessage(
    modifier: Modifier = Modifier,
    errorType: InputErrorType
) {
    Text(
        modifier = modifier
            .padding(
                start = 2.dp,
                end = 2.dp,
                top = 2.dp
            )
            .fillMaxWidth(),
        text = stringResource(inputErrorMessage(errorType)),
        style = LendyFontStyle.medium12,
        color = Red
    )
}

@Composable
fun LendyBasicInput(
    modifier: Modifier = Modifier,
    label: String,
    errorType: InputErrorType,
    textField: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                start = 30.dp,
                end = 30.dp
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        InputLabel(label = label)
        textField()
        ErrorMessage(errorType = errorType)
    }
}

@Composable
fun LendyInput(
    label: String,
    input: String,
    hint: String,
    imeAction: ImeAction,
    errorType: InputErrorType,
    onValueChange: (String) -> Unit
) {
    LendyBasicInput(
        label = label,
        errorType = errorType,
        textField = {
            LendyTextField(
                input = input,
                hint = hint,
                imeAction = imeAction,
                onValueChange = onValueChange
            )
        }
    )
}

@Composable
fun LendyPasswordInput(
    label: String,
    input: String,
    hint: String,
    imeAction: ImeAction,
    errorType: InputErrorType,
    onValueChange: (String) -> Unit
) {
    LendyBasicInput(
        label = label,
        errorType = errorType,
        textField = {
            LendyPasswordTextField(
                input = input,
                hint = hint,
                imeAction = imeAction,
                onValueChange = onValueChange
            )
        }
    )
}

@Composable
fun LendyNumberInput(
    label: String,
    input: String,
    imeAction: ImeAction,
    errorType: InputErrorType,
    onValueChange: (String) -> Unit
) {
    LendyBasicInput(
        label = label,
        errorType = errorType,
        textField = {
            LendyNumberTextField(
                input = input,
                imeAction = imeAction,
                onValueChange = onValueChange
            )
        }
    )
}