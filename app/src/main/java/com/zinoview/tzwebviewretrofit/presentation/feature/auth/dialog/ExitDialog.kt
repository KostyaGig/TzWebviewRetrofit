package com.zinoview.tzwebviewretrofit.presentation.feature.auth.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import java.lang.ref.WeakReference

interface ExitDialog {

    fun show()

    class Base(
        context: Context,
        private val exit: () -> Unit,
    ) : ExitDialog {

        private val weakContext = WeakReference(context)
        private var dialog: Dialog? = null

        override fun show() {
            if (dialog == null) {
                dialog = AlertDialog.Builder(weakContext.get()!!)
                    .setTitle(TITLE)
                    .setMessage(MESSAGE)
                    .setPositiveButton(POSITIVE_TEXT) { dialog,position ->
                        dialog.cancel()
                        exit.invoke()
                    }
                    .setNegativeButton(NEGATIVE_TEXT) { dialog,position ->
                        dialog.cancel()
                    }
                    .create()
                dialog?.show()
            } else {
                dialog?.show()
            }
        }

        private companion object {
            private const val TITLE = "Exit"
            private const val MESSAGE = "You are really wont exit from app?"
            private const val POSITIVE_TEXT = "Yes"
            private const val NEGATIVE_TEXT = "No"
        }
    }
}