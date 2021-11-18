package com.zinoview.tzwebviewretrofit.presentation

import android.webkit.WebView
import com.zinoview.tzwebviewretrofit.core.Abstract
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.AuthActivity
import com.zinoview.tzwebviewretrofit.presentation.feature.webview.log
import com.zinoview.tzwebviewretrofit.presentation.nav.Navigator

interface UiResponse : Abstract.Response {

    fun loadPage(webView: WebView)

    fun makeAction(webView: WebView,navigator: Navigator)

    class Base(
        private val firstParam: String,
        private val secondParam: String,
        private val siteUrl: String
    ) : UiResponse {

        override fun <T : Abstract.Response> map(mapper: Abstract.ResponseMapper<T>): T
            = mapper.map(firstParam, secondParam, siteUrl)

        override fun loadPage(webView: WebView) {
            webView.loadUrl(siteUrl)
        }

        override fun makeAction(webView: WebView,navigator: Navigator) {
            log("make action site url $siteUrl")
            if (siteUrl.isEmpty()) {
                navigator.navigateTo(AuthActivity())
            } else {
                webView.loadUrl(siteUrl)
            }
        }

    }
}