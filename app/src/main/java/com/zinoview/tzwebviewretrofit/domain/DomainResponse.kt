package com.zinoview.tzwebviewretrofit.domain

import com.zinoview.tzwebviewretrofit.core.Abstract

interface DomainResponse : Abstract.Response {

    class Base(
        private val firstParam: String,
        private val secondParam: String,
        private val siteUrl: String
    ) : DomainResponse {

        override fun <T : Abstract.Response> map(mapper: Abstract.ResponseMapper<T>): T
            = mapper.map(firstParam, secondParam, siteUrl)
    }
}