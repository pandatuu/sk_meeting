package com.example.facetime.login.view

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import com.example.facetime.conference.view.MenuActivity
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar1: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            relativeLayout {
                relativeLayout {
                    backgroundColor=Color.TRANSPARENT
                    toolbar1 = toolbar {
                        backgroundColor=Color.TRANSPARENT
                        isEnabled = true
                        title = ""
                    }.lparams {
                        width = matchParent
                        height = dip(0)
                        alignParentBottom()

                    }
                }.lparams {
                    width = matchParent
                    height = dip(0)
                }
                backgroundResource = R.mipmap.bg
                imageView {
                    imageResource = R.drawable.logo
                    scaleType = ImageView.ScaleType.FIT_CENTER
                }.lparams(wrapContent, wrapContent){
                    centerInParent()
                }
                imageView {
                    imageResource = R.drawable.bottom
                    scaleType = ImageView.ScaleType.FIT_CENTER
                }.lparams{
                    alignParentBottom()
                    bottomMargin = dip(36)
                }
            }
        }
        //广告页延迟
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.postDelayed({
            startActivity<MenuActivity>()
            overridePendingTransition(
                R.anim.fade_in_out,
                R.anim.fade_in_out
            )
            finish()
        },2000)
    }

    override fun onRestart() {
        super.onRestart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MainActivity, 0, toolbar1)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}