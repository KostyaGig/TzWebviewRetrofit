package com.zinoview.tzwebviewretrofit.presentation.feature.auth

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.zinoview.tzwebviewretrofit.presentation.Observe
import com.zinoview.tzwebviewretrofit.presentation.feature.webview.log

interface AuthViewModel : Observe<AuthState> {

    fun register(login: String,password: String)

    fun login(login: String,password: String)

    fun restore(login: String)

    fun clean()

    class Base(
        private val auth: Auth
    ) : AuthViewModel, ViewModel() {

        init {
            log("ViewModel init ${hashCode()}")
        }

        override fun register(login: String, password: String) {
            auth.register(login, password)
        }

        override fun login(login: String, password: String) {
            auth.login(login, password)
        }

        override fun restore(login: String) {
            auth.restore(login)
        }

        override fun clean() {
            auth.clean()
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<AuthState>) {
            auth.observe(owner, observer)
        }
    }
}