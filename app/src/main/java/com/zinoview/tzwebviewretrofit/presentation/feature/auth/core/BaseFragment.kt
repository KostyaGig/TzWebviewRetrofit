package com.zinoview.tzwebviewretrofit.presentation.feature.auth.core

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.zinoview.tzwebviewretrofit.core.ResourceProvider
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.AuthActivity
import com.zinoview.tzwebviewretrofit.presentation.feature.webview.MainActivity

/**
 *  MOCK_LOGIN = "mock_login"
    MOCK_PASSWORD = "12345"
 */

abstract class BaseFragment(@LayoutRes id: Int) : Fragment(id) {

    private val toolbar by lazy {
        (requireActivity() as AuthActivity).supportActionBar
    }

    protected val navigator by lazy {
        (requireActivity() as AuthActivity).navigator
    }

    private val resourceProvider by lazy {
        ResourceProvider.Base(requireContext())
    }

    protected fun changeToolbarTitle(@StringRes id: Int) {
        toolbar?.title = resourceProvider.string(id)
    }



    abstract fun navigateToBack()
}