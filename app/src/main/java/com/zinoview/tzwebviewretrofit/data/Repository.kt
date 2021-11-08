package com.zinoview.tzwebviewretrofit.data

import com.zinoview.tzwebviewretrofit.data.cloud.CloudDataSource
import com.zinoview.tzwebviewretrofit.data.cloud.CloudResponse
import com.zinoview.tzwebviewretrofit.data.prefs.ResponseSharedPreferences

interface Repository {

    suspend fun data() : DataResponse

    suspend fun auth(cloudResponse: CloudResponse) : DataResponse

    suspend fun saveUrl(url: String)

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val dataResponseMapper: DataResponseMapper,
        private val responseSharedPreferences: ResponseSharedPreferences
    ) : Repository {

        override suspend fun data(): DataResponse {
            return try {
                val cloudResponse = cloudDataSource.data()
                if (cloudResponse.urlIsEmpty()) {
                    return if (responseSharedPreferences.isNotEmpty()) {
                        val url = responseSharedPreferences.read()
                        cloudResponse.map(url)
                    } else {
                        cloudResponse.map(dataResponseMapper)
                    }
                } else {
                    auth(cloudResponse)
                }
            } catch (e: Exception) {
                DataResponse.Base("(Repository)Happened error at fetching data from network: ${e.message}","","")
            }
        }

        override suspend fun saveUrl(url: String)
            = responseSharedPreferences.save(url)

        override suspend fun auth(cloudResponse: CloudResponse): DataResponse {
            return cloudResponse.map(dataResponseMapper)
        }
    }
}