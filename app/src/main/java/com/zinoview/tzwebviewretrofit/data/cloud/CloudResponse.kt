package com.zinoview.tzwebviewretrofit.data.cloud

import com.google.gson.annotations.SerializedName
import com.zinoview.tzwebviewretrofit.core.Abstract
import com.zinoview.tzwebviewretrofit.data.DataResponse
import com.zinoview.tzwebviewretrofit.log

interface CloudResponse : Abstract.Response {

    fun map(url: String) : DataResponse

    class Base(
        @SerializedName("asdf")
        private val firstParam: String,
        @SerializedName("qqqq")
        private val secondParam: String,
        @SerializedName("url")
        private val siteUrl: String
    ) : CloudResponse {

        override fun <T : Abstract.Response> map(mapper: Abstract.ResponseMapper<T>): T
            = mapper.map(firstParam, secondParam, siteUrl)

        override fun map(url: String) : DataResponse
            = DataResponse.Base(firstParam, secondParam, url)

    }
}