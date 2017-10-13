package com.slamli.mydemo

//import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.WebSettings
//import android.webkit.SslErrorHandler
//import android.webkit.WebSettings
//import android.webkit.WebView
//import android.webkit.WebViewClient

import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * Created by slam.li on 2017/8/25.
 */
class WebViewActivity: AppCompatActivity() {
    var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        webView = findViewById(R.id.webview) as WebView
        webView?.settings?.javaScriptEnabled = true
        webView?.settings?.supportZoom()
        webView?.settings?.displayZoomControls = false
        webView?.settings?.builtInZoomControls = true
        webView?.settings?.domStorageEnabled = true
        if (Build.VERSION.SDK_INT >= 21) {
            webView?.settings?.mixedContentMode = WebSettings.LOAD_NORMAL
        }
        webView?.settings?.loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19

        webView?.loadUrl("https://www.baidu.com")
//        webView?.loadUrl("http://haomaiyi.ews.m.jaeapp.com/article?pk=156&user_id=3999012&key=5d13e9d0b62f49267fc9379146973497ab073dba")
        webView?.setWebViewClient(object: WebViewClient() {
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                super.onReceivedSslError(view, handler, error)
                handler?.proceed()
            }
        })
    }
}