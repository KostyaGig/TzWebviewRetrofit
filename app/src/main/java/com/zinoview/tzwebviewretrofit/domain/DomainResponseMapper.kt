package com.zinoview.tzwebviewretrofit.domain

import com.zinoview.tzwebviewretrofit.core.Abstract

interface DomainResponseMapper : Abstract.ResponseMapper<DomainResponse> {

    class Base : DomainResponseMapper {

        override fun map(firstParam: String, secondParam: String, siteUrl: String): DomainResponse
            = DomainResponse.Base(firstParam, secondParam, siteUrl)
    }
}