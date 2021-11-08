package com.zinoview.tzwebviewretrofit.presentation.nav

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.zinoview.tzwebviewretrofit.R
import com.zinoview.tzwebviewretrofit.presentation.feature.auth.core.BaseFragment
import java.lang.ref.WeakReference

interface Navigator {

    fun navigateTo(fragment: BaseFragment)

    fun navigateTo(activity: Activity)

    class Base(
         activity: AppCompatActivity
    ) : Navigator {

        private val weakMainActivity = WeakReference(activity)

        override fun navigateTo(fragment: BaseFragment) {
            weakMainActivity.get()!!.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit()
        }

        override fun navigateTo(activity: Activity) {
            val activityIntent = Intent(weakMainActivity.get(),activity::class.java)
            weakMainActivity.get()!!.startActivity(activityIntent)
            weakMainActivity.get()!!.finish()
        }

    }
}