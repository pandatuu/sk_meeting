package com.example.facetime.login

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import com.example.facetime.conference.MenuActivity
import org.jetbrains.anko.*
import java.lang.Thread.sleep

@SuppressLint("Registered")
class MainActivity : AppCompatActivity(){
    private var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        frameLayout {
            backgroundResource = R.mipmap.company_bg
        }
        Thread(Runnable {
            sleep(2000)
            determination()
        }).start()
    }

    private fun determination(){
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        val token = sp.getString("token", "")
        println("本机token为：$token")
        if (token.isNullOrEmpty()){
            startActivity<StartActivity>()
            finish()
        }else{
            startActivity<MenuActivity>()
            finish()
        }

    }
}