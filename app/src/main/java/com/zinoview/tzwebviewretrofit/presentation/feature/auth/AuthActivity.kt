package com.zinoview.tzwebviewretrofit.presentation.feature.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.zinoview.tzwebviewretrofit.R
import com.zinoview.tzwebviewretrofit.core.ResponseApp
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.core.BaseFragment
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.fragment.RegisterFragment
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.dialog.ExitDialog
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.dialog.NetworkConnectionDialog
import com.zinoview.tzwebviewretrofit.presentation.nav.ExitActivity
import com.zinoview.tzwebviewretrofit.presentation.nav.Navigator

class AuthActivity : AppCompatActivity(), ExitActivity {

    lateinit var navigator: Navigator

    private lateinit var exitDialog: ExitDialog

    val authViewModel by lazy {
        (application as ResponseApp).authViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val networkConnectionDialog = NetworkConnectionDialog.Base(this)
        val networkConnection = NetworkConnection.Base(this)
        networkConnection.handle(networkConnectionDialog)

        navigator = Navigator.Base(this)
        navigator.navigateTo(RegisterFragment())

        exitDialog = ExitDialog.Base(this) {
            finish()
        }
    }

    override fun exit() {
        exitDialog.show()
    }

    override fun onBackPressed() {
        val baseFragment = supportFragmentManager.fragments[0] as BaseFragment
        baseFragment.navigateToBack()
    }

}