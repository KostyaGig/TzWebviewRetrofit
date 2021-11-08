package com.zinoview.tzwebviewretrofit.data.cloud

interface CloudDataSource {

    suspend fun data() : CloudResponse.Base

    class Base(
        private val apiService: ApiService
    ) : CloudDataSource {

        override suspend fun data(): CloudResponse.Base
            = apiService.data(ID)

        private companion object {
            private const val ID = "2gy3oyj4vsvzmo484hxp"
        }
    }
}