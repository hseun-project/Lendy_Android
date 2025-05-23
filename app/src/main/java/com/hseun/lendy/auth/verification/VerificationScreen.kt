package com.hseun.lendy.auth.verification

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.hseun.lendy.auth.verification.viewmodel.VerificationViewModel
import com.hseun.lendy.ui.utils.LoadingView

const val NAVIGATION_VERIFICATION = "verification"

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VerificationScreen(
    viewModel: VerificationViewModel = hiltViewModel(),
    userId: String,
    navigationGoBack: () -> Unit,
    navToSignIn: () -> Unit
) {
    val context = LocalContext.current
    var webView: WebView? by remember { mutableStateOf(null) }

    val currentUrl = viewModel.url
    val isNavigateToSignIn = viewModel.isNavigateToLogin
    val isLoading = viewModel.isLoading
    val isGetUrl = viewModel.isGetUrl

    LaunchedEffect(Unit) {
        if (!isLoading && isGetUrl == null) {
            viewModel.getStartUrl(userId)
        } else if (isGetUrl == false) {
            Toast.makeText(context, "본인인증 화면을 불러오는 데 실패했습니다", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(isNavigateToSignIn) {
        if (isNavigateToSignIn) {
            navToSignIn()
        }
    }

    val onPressBack = {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        } else {
            navigationGoBack()
        }
    }

    BackHandler {
        onPressBack()
    }

    if (isLoading) {
        LoadingView()
        return
    }

    if (isGetUrl == true) {
        AndroidView(
            factory = {
                val myWebView = WebView(it)
                myWebView.webViewClient = CustomWebViewClient { url ->
                    viewModel.onUrlLoaded(url)
                }

                myWebView.settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
                    loadsImagesAutomatically = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                    textZoom = 100
                    mediaPlaybackRequiresUserGesture = false
                }

                myWebView.apply {
                    webView = this
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            update = {
                Log.d("url", currentUrl)
                Log.d("current", it.url.toString())
                viewModel.onUrlLoaded(it.url.toString())
                it.loadUrl(currentUrl)
            }
        )
    }
}

class CustomWebViewClient(
    private val onRedirectToFinalUrl: (String) -> Unit
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()
        if (url.startsWith("http://localhost:8080/open")) {
            onRedirectToFinalUrl(url)
            return true
        }
        return false
    }
}