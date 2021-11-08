package com.zinoview.tzwebviewretrofit.data

import com.zinoview.tzwebviewretrofit.core.Abstract

interface DataResponse : Abstract.Response {

    class Base(
        private val firstParam: String,
        private val secondParam: String,
        private val siteUrl: String
    ) : DataResponse {

        override fun <T : Abstract.Response> map(mapper: Abstract.ResponseMapper<T>): T
            = mapper.map(firstParam, secondParam, siteUrl)
    }
}