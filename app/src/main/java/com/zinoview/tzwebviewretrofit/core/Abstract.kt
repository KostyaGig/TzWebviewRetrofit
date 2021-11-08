package com.zinoview.tzwebviewretrofit.core

interface Abstract {

    interface Response {

        fun <T : Response> map(mapper: ResponseMapper<T>) : T
    }

    interface ResponseMapper<T : Response> : Mapper {

        fun map(firstParam: String,secondParam: String,siteUrl: String) : T
    }

    interface Mapper
}