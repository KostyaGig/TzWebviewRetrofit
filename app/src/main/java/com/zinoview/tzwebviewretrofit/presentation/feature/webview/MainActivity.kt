package com.zinoview.tzwebviewretrofit.presentation.feature.webview

import android.Manifest
import android.app.DownloadManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.zinoview.tzwebviewretrofit.R
import com.zinoview.tzwebviewretrofit.core.ResourceProvider
import com.zinoview.tzwebviewretrofit.core.ResponseApp
import com.zinoview.tzwebviewretrofit.presentation.nav.Navigator


//for test
fun Any?.log(message: String) {
    Log.d("zinoviewk",message)
}

class MainActivity : AppCompatActivity() {

    private companion object {
        private const val USER_AGENT = "Android"
    }

    private val responseApp by lazy {
        application as ResponseApp
    }

    private val viewModel by lazy {
        responseApp.mainViewModel
    }


    private lateinit var webView: WebView
    private lateinit var requestAccessWriteExternalStoragePermission: ActivityResultLauncher<String>

    private var permissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resourceProvider = ResourceProvider.Base(responseApp)

        val navigator = Navigator.Base(this)

        webView = findViewById(R.id.webView)
        with(webView.settings) {
            javaScriptEnabled = true
            userAgentString = USER_AGENT
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = true
        }

        requestAccessWriteExternalStoragePermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted ->
            permissionGranted = isGranted
        }

        webView.setInitialScale(1)
        webView.webViewClient = object : WebViewClient() {

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {

            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }

        webView.setDownloadListener { url, p1, p2, p3, p4 ->
                if (permissionGranted) {
                    downloadFile(url)
                    Toast.makeText(this,resourceProvider.string(R.string.downloading_file_text),Toast.LENGTH_SHORT).show()
                } else {
                    requestAccessWriteExternalStoragePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    if (permissionGranted) {
                        downloadFile(url)
                        Toast.makeText(this,resourceProvider.string(R.string.downloading_file_text),Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this,resourceProvider.string(R.string.give_permission_text),Toast.LENGTH_SHORT).show()
                    }
                }
            }

        if (savedInstanceState == null) {
            log("savedstate == null")
            viewModel.data()
            viewModel.observe(this) { uiResponse ->
                uiResponse.makeAction(webView,navigator)
            }
        } else {
            webView.restoreState(savedInstanceState)
        }
    }

    private fun downloadFile(url: String) {
        //todo move to class
        val downloadManagerRequest = DownloadManager.Request(Uri.parse(url))
        with(downloadManagerRequest) {
            allowScanningByMediaScanner()
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,System.currentTimeMillis().toString())
        }
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(downloadManagerRequest)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    //backstack for webView
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val actionEvent = event?.action
        actionEvent?.let { appEvent ->
            if (appEvent == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        }
        return true
    }

    override fun onPause() {
        val webViewUrl = webView.url ?: ""
        viewModel.saveUrl(webViewUrl)
        super.onPause()
    }

}