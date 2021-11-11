package com.zinoview.tzwebviewretrofit.presentation.feature.auth

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.zinoview.tzwebviewretrofit.presentation.Observe
import com.zinoview.tzwebviewretrofit.presentation.feature.webview.log
import kotlinx.coroutines.*

interface Auth : Observe<AuthState> {

    fun register(login: String,password: String)

    fun login(login: String,password: String)

    fun restore(login: String)

    fun clean()

    class Base(
        private val field: Field,
        private val authCommunication: AuthCommunication
    ) : Auth {

        init {
            log("Auth instance ${hashCode()}")
        }

        override fun register(login: String, password: String) {
            authCommunication.postValue(AuthState.Progress())
            CoroutineScope(Dispatchers.IO).launch {
                delay(DELAY.toLong())
                if (firstStepIsCompleted(login, password)) {
                    withContext(Dispatchers.Main) {
                        authCommunication.postValue(AuthState.Success(SUCCESS_REGISTER))
                    }
                }
            }

        }

        override fun login(login: String, password: String) {
            authCommunication.postValue(AuthState.Progress())
            CoroutineScope(Dispatchers.IO).launch {
                delay(DELAY.toLong())
                if (firstStepIsCompleted(login, password)) {
                    withContext(Dispatchers.Main) {
                        authCommunication.postValue(AuthState.Success(SUCCESS_LOGIN))
                    }
                }
            }
        }

        override fun restore(login: String) {
            authCommunication.postValue(AuthState.Progress())
            CoroutineScope(Dispatchers.IO).launch {
                delay(DELAY.toLong())
                if (field.isValidLogin(login)) {
                    withContext(Dispatchers.Main) {
                        authCommunication.postValue(AuthState.Success(SUCCESS_RESTORE))
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        authCommunication.postValue(AuthState.Failure(INVALID_LOGIN))
                    }
                }
            }

        }

        private fun firstStepIsCompleted(login: String, password: String) : Boolean  {
            val fields = listOf(login,password)

            if (field.isEmpty(fields)) {
                CoroutineScope(Dispatchers.Main).launch {
                    authCommunication.postValue(AuthState.Failure(EMPTY_FIELDS_MESSAGE))
                }
                return false
            }

            return if (field.isNotValid(fields)) {
                CoroutineScope(Dispatchers.Main).launch {
                    authCommunication.postValue(AuthState.Failure(INVALID_DATA_USER))
                }
                false
            } else {
                true
            }

        }

        override fun clean() {
            authCommunication.clean()
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<AuthState>) {
            authCommunication.observe(owner, observer)
        }

        private companion object {
            private const val EMPTY_FIELDS_MESSAGE = "Fields now will be empty"
            private const val INVALID_DATA_USER = "Invalid login or password"
            private const val INVALID_LOGIN = "Invalid login user"

            private const val SUCCESS_REGISTER = "Success register!"
            private const val SUCCESS_LOGIN = "Success login!"
            private const val SUCCESS_RESTORE = "Success restore account!"

            private const val DELAY = 4000
        }
    }
}