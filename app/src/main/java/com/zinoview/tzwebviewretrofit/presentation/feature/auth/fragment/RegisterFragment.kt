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
import com.zinoview.tzwebviewretrofit.presentation.nav.ExitActivity

class RegisterFragment : BaseFragment(R.layout.register_fragment) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeToolbarTitle(R.string.register_text)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val auth = Auth.Base(Field.Base(),AuthCommunication.Base())

        val loginField = view.findViewById<EditText>(R.id.login_field)
        val passwordField = view.findViewById<EditText>(R.id.password_field)
        val authBtn = view.findViewById<Button>(R.id.auth_btn)
        val authResultTextView = view.findViewById<TextView>(R.id.auth_tv)
        val authProgressBar = view.findViewById<ProgressBar>(R.id.auth_pb)
        val alreadyExistAccountTextView = view.findViewById<TextView>(R.id.exist_account_tv)
        val forgotPasswordAccountTextView = view.findViewById<TextView>(R.id.forgot_tv)

        authBtn.setOnClickListener {
            auth.register(loginField.text.trim().toString(),passwordField.text.trim().toString())
        }

        auth.observe(this) { authState ->
            authState.map(authProgressBar,authResultTextView)
        }

        alreadyExistAccountTextView.setOnClickListener {
            navigator.navigateTo(LoginFragment())
        }

        forgotPasswordAccountTextView.setOnClickListener {
            navigator.navigateTo(RestoreAccountFragment())
        }

    }

    override fun navigateToBack()
        = (requireActivity() as ExitActivity).exit()

}