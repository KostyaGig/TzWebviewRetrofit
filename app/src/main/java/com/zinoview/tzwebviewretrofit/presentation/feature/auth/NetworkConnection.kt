package com.zinoview.tzwebviewretrofit.presentation.feature.auth

import android.content.Context
import android.net.ConnectivityManager
import com.zinoview.tzwebviewretrofit.presentation.feature.dialog.NetworkConnectionDialog


interface NetworkConnection {

    fun handle(networkConnectionDialog: NetworkConnectionDialog)

    class Base(
        private val context: Context
    ) : NetworkConnection {



        override fun handle(networkConnectionDialog: NetworkConnectionDialog) {
            if (isNotAvailable()) {
                networkConnectionDialog.show()
            }
        }

        private fun isNotAvailable(): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return (networkInfo != null && networkInfo.isConnected).not()
        }
    }
}

