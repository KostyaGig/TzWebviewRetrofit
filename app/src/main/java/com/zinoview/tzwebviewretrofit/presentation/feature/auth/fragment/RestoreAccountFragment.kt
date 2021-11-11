package com.zinoview.tzwebviewretrofit.presentation.feature.auth.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.zinoview.tzwebviewretrofit.R
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.Auth
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.AuthCommunication
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.Field
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.FragmentState
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.core.BaseFragment

class RestoreAccountFragment : BaseFragment(R.layout.restore_account_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeToolbarTitle(R.string.restore_text)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val loginField = view.findViewById<EditText>(R.id.login_field)
        val authBtn = view.findViewById<Button>(R.id.auth_btn)
        val authProgressBar = view.findViewById<ProgressBar>(R.id.auth_pb)
        val authResultTextView = view.findViewById<TextView>(R.id.auth_tv)

        authBtn.setOnClickListener {
            authViewModel.restore(loginField.text.trim().toString())
        }

        authViewModel.observe(this) { authState ->
            authState.map(authProgressBar,authResultTextView)
        }
    }

    override fun navigateToBack()
        = navigator.navigateTo(LoginFragment())



}