package com.zinoview.tzwebviewretrofit.presentation.feature.auth.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import com.zinoview.tzwebviewretrofit.R
import java.lang.ref.WeakReference

interface NetworkConnectionDialog {

    fun show()

    class Base(
        context: Context
    ) : NetworkConnectionDialog {

        private val weakContext = WeakReference(context)
        private var dialog: Dialog? = null

        override fun show() {
            if (dialog == null) {
                dialog = AlertDialog.Builder(weakContext.get()!!)
                    .setTitle(TITLE)
                    .setMessage(MESSAGE)
                    .setIcon(R.drawable.ic_error)
                    .setPositiveButton(POSITIVE_TEXT) { dialog, position ->
                        dialog.cancel()
                    }
                    .create()
                dialog?.show()
            } else {
                dialog?.show()
            }

        }

        private companion object {
            private const val TITLE = "Error"
            private const val MESSAGE = "Please,check your internet connection status and restrat app"
            private const val POSITIVE_TEXT = "Ok"
        }
    }
}