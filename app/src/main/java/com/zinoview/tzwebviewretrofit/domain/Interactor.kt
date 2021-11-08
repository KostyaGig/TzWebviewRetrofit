package com.zinoview.tzwebviewretrofit.domain

import com.zinoview.tzwebviewretrofit.data.Repository

interface Interactor {

    suspend fun data() : DomainResponse

    suspend fun saveUrl(url: String)

    class Base(
        private val repository: Repository,
        private val domainResponseMapper: DomainResponseMapper
    ) : Interactor {

        override suspend fun data(): DomainResponse {
            val dataResponse = repository.data()
            return dataResponse.map(domainResponseMapper)
        }

        override suspend fun saveUrl(url: String) {
            repository.saveUrl(url)
        }

    }

}