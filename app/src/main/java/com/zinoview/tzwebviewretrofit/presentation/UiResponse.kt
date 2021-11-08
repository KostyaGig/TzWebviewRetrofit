package com.zinoview.tzwebviewretrofit.presentation

import android.webkit.WebView
import com.zinoview.tzwebviewretrofit.core.Abstract

interface UiResponse : Abstract.Response {


    fun loadPage(webView: WebView)

    //todo remove later
    fun url() : String
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

        override fun url() = siteUrl

    }
}