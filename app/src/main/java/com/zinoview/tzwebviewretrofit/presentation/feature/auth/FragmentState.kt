package com.zinoview.tzwebviewretrofit.presentation.feature.auth

import android.view.View
import android.widget.ProgressBar
import java.io.Serializable

interface FragmentState : Serializable {

    fun restore(progressBar: ProgressBar)

    fun changeProgressVisibility(value: Int)

    class Base : FragmentState {

        private var progressVisibility = View.GONE

        override fun restore(progressBar: ProgressBar) {
            progressBar.visibility = progressVisibility
        }

        override fun changeProgressVisibility(value: Int) {
            progressVisibility = value
        }
    }
}