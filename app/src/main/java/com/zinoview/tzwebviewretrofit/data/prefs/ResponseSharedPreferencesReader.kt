package com.zinoview.tzwebviewretrofit.data.prefs

import android.content.SharedPreferences

interface ResponseSharedPreferencesReader {

    fun read(sharedPreferences: SharedPreferences,key: String) : String

    class Base : ResponseSharedPreferencesReader {

        override fun read(sharedPreferences: SharedPreferences, key: String) : String
            = sharedPreferences.getString(key,"") ?: ""
    }
}