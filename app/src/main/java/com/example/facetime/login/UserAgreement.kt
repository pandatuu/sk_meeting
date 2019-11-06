package com.example.facetime.login

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.*

class UserAgreement:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayout {
            scrollView {
                webView {
//                    loadUrl("https://o.skjob.jp/login/serviceforios")
                    this.settings.javaScriptEnabled = true
                    //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
                    this.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                            view?.loadUrl(request?.url.toString())
                            return true
                        }
                    }
                    this.loadUrl("https://blog.csdn.net/cysion1989/article/details/73431003")
                }.lparams(width = matchParent,height = matchParent){}
            }.lparams(width = matchParent,height = wrapContent){
                weight = 1f
            }
        }
    }
}
