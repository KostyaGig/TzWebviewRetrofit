package com.zinoview.tzwebviewretrofit.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.tzwebviewretrofit.domain.Interactor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface MainViewModel : Observe<UiResponse> {

    fun data()

    fun saveUrl(url: String)

    class Base(
        private val interactor: Interactor,
        private val uiResponseMapper: UiResponseMapper,
        private val responseCommunication: ResponseCommunication,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : MainViewModel, ViewModel() {

        override fun data() {
            viewModelScope.launch(defaultDispatcher) {
                val domainResponse = interactor.data()
                val uiResponse = domainResponse.map(uiResponseMapper)

                withContext(Dispatchers.Main) {
                    responseCommunication.postValue(uiResponse)
                }
            }
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<UiResponse>)
            = responseCommunication.observe(owner,observer)

        override fun saveUrl(url: String) {
            viewModelScope.launch(defaultDispatcher) {
                interactor.saveUrl(url)
            }
        }
    }
}