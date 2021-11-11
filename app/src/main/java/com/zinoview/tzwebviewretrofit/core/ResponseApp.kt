package com.zinoview.tzwebviewretrofit.core

import android.app.Application
import com.onesignal.OneSignal
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import com.zinoview.tzwebviewretrofit.R
import com.zinoview.tzwebviewretrofit.data.DataResponseMapper
import com.zinoview.tzwebviewretrofit.data.Repository
import com.zinoview.tzwebviewretrofit.data.cloud.ApiService
import com.zinoview.tzwebviewretrofit.data.cloud.CloudDataSource
import com.zinoview.tzwebviewretrofit.data.prefs.ResponseSharedPreferences
import com.zinoview.tzwebviewretrofit.data.prefs.ResponseSharedPreferencesReader
import com.zinoview.tzwebviewretrofit.domain.DomainResponseMapper
import com.zinoview.tzwebviewretrofit.domain.Interactor
import com.zinoview.tzwebviewretrofit.presentation.MainViewModel
import com.zinoview.tzwebviewretrofit.presentation.ResponseCommunication
import com.zinoview.tzwebviewretrofit.presentation.UiResponseMapper
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.Auth
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.AuthCommunication
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.AuthViewModel
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.Field
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResponseApp : Application() {

    private companion object {
        private const val BASE_URL = "https://sporter1.ru"
    }

    lateinit var mainViewModel: MainViewModel
    lateinit var authViewModel: AuthViewModel

    override fun onCreate() {
        super.onCreate()

        val resourceProvider = ResourceProvider.Base(this)

        //init yandex metric
        val config = YandexMetricaConfig.newConfigBuilder(resourceProvider.string(R.string.yandex_metrics_api_key)).build()
        YandexMetrica.activate(this,config)
        YandexMetrica.enableActivityAutoTracking(this)

        //init one signal
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        OneSignal.initWithContext(this);
        OneSignal.setAppId(resourceProvider.string(R.string.one_signal_api_key));

        val client =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        val cloudDataSource = CloudDataSource.Base(apiService)

        val repository = Repository.Base(
            cloudDataSource,
            DataResponseMapper.Base(),
            ResponseSharedPreferences.Base(
                ResponseSharedPreferencesReader.Base(),
                this
            )
        )

        val interactor = Interactor.Base(
            repository,
            DomainResponseMapper.Base()
        )

        mainViewModel = MainViewModel.Base(
            interactor,
            UiResponseMapper.Base(),
            ResponseCommunication.Base()
        )

        authViewModel = AuthViewModel.Base(
            Auth.Base(
                Field.Base(),
                AuthCommunication.Base()
            )
        )
    }
}