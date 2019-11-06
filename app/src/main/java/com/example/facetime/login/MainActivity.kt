package com.example.facetime.login

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import com.example.facetime.conference.MenuActivity
import com.jaeger.library.StatusBarUtil
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.*
import java.lang.Thread.sleep

@SuppressLint("Registered")
class MainActivity : AppCompatActivity(){
    private var mContext: Context? = null
    private lateinit var toolbar1: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        frameLayout {
            backgroundResource = R.mipmap.company_bg
            relativeLayout() {
                backgroundColor=Color.TRANSPARENT
                toolbar1 = toolbar {
                    backgroundColor=Color.TRANSPARENT
                    isEnabled = true
                    title = ""
                }.lparams() {
                    width = matchParent
                    height = dip(0)
                    alignParentBottom()

                }
            }.lparams() {
                width = matchParent
                height = dip(0)
            }
        }
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            Thread(Runnable {
                sleep(2000)
                determination()
            }).start()
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MainActivity, 0, toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
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