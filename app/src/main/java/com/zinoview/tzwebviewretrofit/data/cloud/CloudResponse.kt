package com.zinoview.tzwebviewretrofit.data.cloud

import com.google.gson.annotations.SerializedName
import com.zinoview.tzwebviewretrofit.core.Abstract
import com.zinoview.tzwebviewretrofit.data.DataResponse

interface CloudResponse : Abstract.Response {

    fun map(url: String) : DataResponse

    fun urlIsEmpty() : Boolean

    class Base(
        @SerializedName("asdf")
        private val firstParam: String,
        @SerializedName("qqqq")
        private val secondParam: String,
        @SerializedName("url")
        private val siteUrl: String = ""
    ) : CloudResponse {

        override fun <T : Abstract.Response> map(mapper: Abstract.ResponseMapper<T>): T
            = mapper.map(firstParam, secondParam, siteUrl)

        override fun map(url: String) : DataResponse
            = DataResponse.Base(firstParam, secondParam, url)

        override fun urlIsEmpty(): Boolean
            = siteUrl.isEmpty()
    }
}