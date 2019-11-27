package com.example.facetime.application

import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import android.preference.PreferenceManager

import com.alibaba.fastjson.JSON
import com.tencent.mm.opensdk.utils.Log
import com.umeng.commonsdk.UMConfigure

import org.json.JSONArray
import org.json.JSONObject

import java.io.BufferedInputStream
import java.io.FileInputStream
import java.net.URL
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

class App : Application() {




    companion object {
        private var instance: App? = null
        fun getInstance(): App? {
            return instance
        }


    }

    //消息socket创建
    //测试地址 https://im.sk.cgland.top/
    //正式地址 https://im.sk.skjob.jp/

    private var messageLoginState = false
    private lateinit var deviceToken: String


    private val defaultPreferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(this)

    override fun onCreate() {
        super.onCreate()

        instance = this

        println("版本!")
        println(Build.VERSION.SDK_INT)

        //注册消息推送
        UMConfigure.init(
            this,
            "5dddec6d0cafb2c6c4000751",
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            ""
        )

    }










    fun getDeviceToken(): String {
        return deviceToken
    }

    fun getMyToken(): String {
        val token = defaultPreferences.getString("token", "") ?: ""
        println("--------------------------------------------------------")
        println("token->" + "Bearer ${token.replace("\"", "")}")

        if (token.isEmpty()) {
//            Thread(Runnable {
//                sleep(200)
//                if(count>15){
//                }else{
//                    getMyToken()
//                    count++
//                }
//            }).start()
        }

        return token.replace("\"", "")
    }

    fun getMyId(): String {
        return defaultPreferences.getString("id", "") ?: ""
    }


    fun getMyLogoUrl(): String {
        var avatarURL = defaultPreferences.getString("avatarURL", "") ?: ""
        val arra = avatarURL.split(",")
        if (arra.isNotEmpty()) {
            avatarURL = arra[0]
        }
        return avatarURL
    }


    fun getMessageLoginState(): Boolean {
        return messageLoginState
    }




}