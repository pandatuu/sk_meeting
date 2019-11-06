package com.example.facetime.login

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import org.jetbrains.anko.*

class ServiceStatement: AppCompatActivity() {
    lateinit var statement:WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayout {
            scrollView {
                statement = webView {
                    //                    loadUrl("https://o.skjob.jp/login/serviceforios")
                    this.settings.javaScriptEnabled = true

                    //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
                    this.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                            view?.loadUrl(request?.url.toString())
                            return true
                        }

                        // 请求发送，等待过程
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                        }

                        // 请求成功，返回结果，取消提示
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                        }
                    }
                    this.loadUrl(R.string.serviceStatementUrl.toString())
                }.lparams(width = matchParent,height = matchParent){}
            }.lparams(width = matchParent,height = wrapContent){
                weight = 1f
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        statement.destroy()
    }
}