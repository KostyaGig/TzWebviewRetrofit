package com.zinoview.tzwebviewretrofit.presentation.feature.auth

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zinoview.tzwebviewretrofit.presentation.Observe

interface AuthCommunication : Observe<AuthState> {

    fun postValue(value: AuthState)

    fun clean()

    class Base : AuthCommunication {

        private val authLiveData = MutableLiveData<AuthState>()

        override fun observe(owner: LifecycleOwner, observer: Observer<AuthState>) {
            authLiveData.observe(owner, observer)
        }

        override fun postValue(value: AuthState) {
            authLiveData.value = value
        }

        override fun clean() {
            authLiveData.value = AuthState.Empty
        }

    }
}