package com.example.facetime.login

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import com.example.facetime.conference.MenuActivity
import org.jetbrains.anko.*
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    //    private lateinit var toolbar1: Toolbar

    private var mContext: Context? = null
    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = this
        super.onCreate(savedInstanceState)
            frameLayout = frameLayout {
                relativeLayout() {
                    backgroundResource = R.mipmap.company_bg
                }.lparams() {
                    width = matchParent
                    height = matchParent
                }
            }
        //启动页延迟
        runOnUiThread {
            sleep(500)
        }
        //广告页延迟
        val mainHandler = Handler(Looper.getMainLooper());
        mainHandler.postDelayed( Runnable() {
            determination()
        },2000)

    }


    private fun determination() {


        startActivity<MenuActivity>()
        overridePendingTransition(
            R.anim.fade_in_out,
            R.anim.fade_in_out
        )
        finish()


//        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
//        val token = sp.getString("token", "")
//        println("本机token为：$token")
//        if (token.isNullOrEmpty()) {
//            startActivity<StartActivity>()
//            overridePendingTransition(
//                R.anim.fade_in_out,
//                R.anim.fade_in_out
//            )
//            finish()
//        } else {
//            startActivity<MenuActivity>()
//            overridePendingTransition(
//                R.anim.fade_in_out,
//                R.anim.fade_in_out
//            )
//            finish()
//        }
    }
}