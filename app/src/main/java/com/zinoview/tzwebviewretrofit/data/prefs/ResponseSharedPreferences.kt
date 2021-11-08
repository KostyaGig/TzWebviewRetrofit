package com.zinoview.tzwebviewretrofit.data.prefs

import android.content.Context

interface ResponseSharedPreferences {

    fun save(url: String)

    fun read() : String

    fun isNotEmpty() : Boolean

    class Base(
        private val sharedPreferencesReader: ResponseSharedPreferencesReader,
        context: Context
    ) : ResponseSharedPreferences {

        private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE)

        override fun save(url: String) {
            if (url.isEmpty()) {
                sharedPreferences.edit().putString(LAST_VISITED_URL_KEY,EMPTY_URL).apply()
            } else {
                sharedPreferences.edit().putString(LAST_VISITED_URL_KEY,url).apply()
            }
        }

        override fun read()
            = sharedPreferencesReader.read(sharedPreferences, LAST_VISITED_URL_KEY)

        override fun isNotEmpty(): Boolean
            = sharedPreferencesReader.read(sharedPreferences, LAST_VISITED_URL_KEY) != EMPTY_URL

        private companion object {

            private const val SHARED_PREFERENCES_NAME = "response_sp"
            private const val LAST_VISITED_URL_KEY = "last_visited_url"
            private const val EMPTY_URL = "empty_url"
        }
    }
}