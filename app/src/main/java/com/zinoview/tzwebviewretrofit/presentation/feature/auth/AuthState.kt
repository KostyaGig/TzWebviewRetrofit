package com.zinoview.tzwebviewretrofit.presentation.feature.auth

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

interface AuthState {

    fun map(progressBar: ProgressBar,textView: TextView)

    class Progress : AuthState {

        override fun map(progressBar: ProgressBar, textView: TextView) {
            progressBar.visibility = View.VISIBLE
            textView.visibility = View.GONE
        }
    }

    class Success(
        private val message: String
    ) : AuthState {

        override fun map(progressBar: ProgressBar, textView: TextView) {
            progressBar.visibility = View.GONE
            textView.text = message
            textView.visibility = View.VISIBLE
        }
    }

    class Failure(
        private val message: String
    ) : AuthState {

        override fun map(progressBar: ProgressBar, textView: TextView) {
            progressBar.visibility = View.GONE
            textView.text = message
            textView.visibility = View.VISIBLE
        }
    }
}