package com.example.facetime.login.view

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import com.example.facetime.util.DialogUtils
import com.example.facetime.util.MyDialog
import org.jetbrains.anko.*

class ServiceStatement : AppCompatActivity() {
    lateinit var statement: WebView
    var thisDialog: MyDialog? = null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something

        if (thisDialog != null && thisDialog?.isShowing!!) {
            val toast = Toast.makeText(this, "网络出错....", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayout {
            scrollView {
                statement = webView {
                    //                    loadUrl("https://o.skjob.jp/login/serviceforios")
                    this.settings.javaScriptEnabled = true

                    //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
                    this.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            view?.loadUrl(request?.url.toString())
                            return true
                        }

                        // 请求发送，等待过程
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            thisDialog = DialogUtils.showLoading(this@ServiceStatement)
                            mHandler.postDelayed(r, 20000)
                        }

                        // 请求成功，返回结果，取消提示
                        override fun onPageFinished(view: WebView?, url: String?) {
                            DialogUtils.hideLoading(thisDialog)
                        }
                    }
                    this.loadUrl(this@ServiceStatement.getString(R.string.serviceStatementUrl))
                }.lparams(width = matchParent, height = matchParent) {}
            }.lparams(width = matchParent, height = wrapContent) {
                weight = 1f
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        statement.destroy()
    }
}