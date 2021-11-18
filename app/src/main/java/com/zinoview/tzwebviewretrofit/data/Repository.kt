package com.zinoview.tzwebviewretrofit.data

import com.zinoview.tzwebviewretrofit.data.cloud.CloudDataSource
import com.zinoview.tzwebviewretrofit.data.cloud.CloudResponse
import com.zinoview.tzwebviewretrofit.data.prefs.ResponseSharedPreferences
import com.zinoview.tzwebviewretrofit.presentation.feature.webview.log

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
                    auth(cloudResponse)
                } else {
                    return if (responseSharedPreferences.isNotEmpty()) {
                        log("Repo shared pref not empty")
                        val url = responseSharedPreferences.read()
                        cloudResponse.map(url)
                    } else {
                        log("Repo shared pref empty")
                        cloudResponse.map(dataResponseMapper)
                    }
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