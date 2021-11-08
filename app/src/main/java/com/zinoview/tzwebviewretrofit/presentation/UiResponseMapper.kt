package com.zinoview.tzwebviewretrofit.presentation

import com.zinoview.tzwebviewretrofit.core.Abstract

interface UiResponseMapper : Abstract.ResponseMapper<UiResponse> {

    class Base : UiResponseMapper {

        override fun map(firstParam: String, secondParam: String, siteUrl: String): UiResponse
            = UiResponse.Base(firstParam, secondParam, siteUrl)
    }
}