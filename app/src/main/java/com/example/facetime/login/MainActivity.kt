package com.example.facetime.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.conference.MenuActivity
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(){
    private var mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        linearLayout {
            backgroundColor = Color.parseColor("#000000")
        }
        determination()
    }

    private fun determination(){
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
        val token = sp.getString("token", "")
        println("本机token为：$token")
        if (token.isNullOrEmpty()){
            startActivity<StartActivity>()
        }else{
            startActivity<MenuActivity>()
        }

    }
}