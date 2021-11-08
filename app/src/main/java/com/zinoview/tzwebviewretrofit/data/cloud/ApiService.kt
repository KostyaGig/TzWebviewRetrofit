package com.zinoview.tzwebviewretrofit.data.cloud

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Base url - https://sporter1.ru/aka.php?id=2gy3oyj4vsvzmo484hxp
 * */

interface ApiService {

    @GET("/aka.php")
    suspend fun data(@Query("id") id: String) : CloudResponse.Base
}