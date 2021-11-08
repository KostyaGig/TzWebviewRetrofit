package com.zinoview.tzwebviewretrofit.data

import com.zinoview.tzwebviewretrofit.core.Abstract

interface DataResponseMapper : Abstract.ResponseMapper<DataResponse> {

    class Base : DataResponseMapper {

        override fun map(firstParam: String, secondParam: String, siteUrl: String): DataResponse
            = DataResponse.Base(firstParam, secondParam, siteUrl)
    }
}