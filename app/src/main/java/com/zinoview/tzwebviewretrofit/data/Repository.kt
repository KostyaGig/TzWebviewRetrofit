package com.zinoview.tzwebviewretrofit.data

import com.zinoview.tzwebviewretrofit.data.cloud.CloudDataSource
import com.zinoview.tzwebviewretrofit.data.prefs.ResponseSharedPreferences
import com.zinoview.tzwebviewretrofit.log

interface Repository {

    suspend fun data() : DataResponse

    suspend fun saveUrl(url: String)

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val dataResponseMapper: DataResponseMapper,
        private val responseSharedPreferences: ResponseSharedPreferences
    ) : Repository {

        override suspend fun data(): DataResponse {
            return try {
                val cloudResponse = cloudDataSource.data()
                return if (responseSharedPreferences.isNotEmpty()) {
                    val url = responseSharedPreferences.read()
                    log("(Repository)shared preferences not empty , url $url")
                    cloudResponse.map(url)
                } else {
                    cloudResponse.map(dataResponseMapper)
                }
            } catch (e: Exception) {
                DataResponse.Base("(Repository)Happened error at fetching data from network: ${e.message}","","")
            }
        }

        override suspend fun saveUrl(url: String)
            = responseSharedPreferences.save(url)
    }
}