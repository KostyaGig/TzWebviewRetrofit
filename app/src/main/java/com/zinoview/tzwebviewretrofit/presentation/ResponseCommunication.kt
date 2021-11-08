package com.zinoview.tzwebviewretrofit.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface ResponseCommunication : Observe<UiResponse> {


    fun postValue(value: UiResponse)

    class Base : ResponseCommunication {

        private val uiResponseLiveData = MutableLiveData<UiResponse>()

        override fun observe(owner: LifecycleOwner, observer: Observer<UiResponse>) {
            uiResponseLiveData.observe(owner, observer)
        }

        override fun postValue(value: UiResponse) {
            uiResponseLiveData.value = value
        }
    }
}