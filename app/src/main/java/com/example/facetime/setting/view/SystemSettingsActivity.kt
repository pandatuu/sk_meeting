package com.example.facetime.setting.view


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

import android.preference.PreferenceManager
import android.widget.*
import click
import com.example.facetime.R
import com.example.facetime.login.api.LoginApi
import com.example.facetime.conference.view.MenuActivity
import com.example.facetime.login.view.StartActivity
import com.example.facetime.setting.api.SettingApi
import com.example.facetime.util.DialogUtils
import com.example.facetime.util.MyDialog
import com.example.facetime.util.RetrofitUtils
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import retrofit2.HttpException
import withTrigger


open class SystemSettingsActivity : AppCompatActivity() {


    private lateinit var toolbar1: Toolbar
    var thisDialog: MyDialog? = null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!) {
            val toast = Toast.makeText(
                this@SystemSettingsActivity,
                "ネットワークエラー",
                Toast.LENGTH_SHORT
            )//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mPerferences = PreferenceManager.getDefaultSharedPreferences(this@SystemSettingsActivity)
        val token = mPerferences.getString("token","")

        verticalLayout {
            backgroundColor = Color.parseColor("#f2f2f2")

            linearLayout {
                toolbar1 = toolbar {
                    isEnabled = true
                    title = ""
                    navigationIconResource = R.mipmap.icon_back
                }.lparams() {
                    width = dip(45)
                }

                linearLayout {
                    textView {
                        this.withTrigger().click  {
                            finish()//返回
                            overridePendingTransition(
                                R.anim.left_in,
                                R.anim.right_out
                            )
                        }
                        text="返回"
                        gravity=Gravity.CENTER

                    }.lparams(){
                        height= matchParent
                        width= wrapContent
                    }
                }.lparams() {
                    weight = 1f
                    width = dip(0)
                    height = dip(65 - getStatusBarHeight(this@SystemSettingsActivity))
                    topMargin=dip(getStatusBarHeight(this@SystemSettingsActivity))
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }





            verticalLayout {
                gravity = Gravity.CENTER_HORIZONTAL

                textView {
                    text = "昵称设置"
                    textSize = 14f
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    gravity = Gravity.CENTER

                    this.withTrigger().click  {
                        if(token != ""){
                            startActivity<UpdateNickName>()
                            overridePendingTransition(
                                R.anim.right_in,
                                R.anim.left_out
                            )
                        }else{
                            val toast = Toast.makeText(applicationContext, "请先登录账号", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            startActivity<StartActivity>()
                            overridePendingTransition(
                                R.anim.right_in,
                                R.anim.left_out
                            )
                        }
                    }
                }.lparams() {
                    height = dip(60)
                    width = matchParent
                }

                textView {
                   backgroundColor= Color.parseColor("#cccccc")
                }.lparams() {
                    height = dip(px2sp(1))
                    width = matchParent
                }

                textView {
                    text = "修改密码"
                    textSize = 14f
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    gravity = Gravity.CENTER
                    this.withTrigger().click  {

                        if(token != ""){
                            startActivity<UpdatePassword>()
                            overridePendingTransition(
                                R.anim.right_in,
                                R.anim.left_out
                            )
                        }else{
                            val toast = Toast.makeText(applicationContext, "请先登录账号", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            startActivity<StartActivity>()
                            overridePendingTransition(
                                R.anim.right_in,
                                R.anim.left_out
                            )
                        }
                    }
                }.lparams() {
                    height = dip(60)
                    width = matchParent
                }

                textView {
                    backgroundColor= Color.parseColor("#cccccc")

                }.lparams() {
                    height = dip(px2sp(1))
                    width = matchParent
                }

//                textView {
//                    text = "修改服务器地址"
//                    textSize = 14f
//                    textColor = Color.WHITE
//                    backgroundResource = R.drawable.bottonbg
//                    gravity = Gravity.CENTER
//                    this.withTrigger().click  {
//                        startActivity<SetServiceAddr>()
//                        overridePendingTransition(
//                            R.anim.right_in,
//                            R.anim.left_out
//                        )
//                    }
//                }.lparams() {
//                    height = dip(60)
//                    width = matchParent
//                }
//
//                textView {
//                    backgroundColor= Color.parseColor("#cccccc")
//
//                }.lparams() {
//                    height = dip(px2sp(1))
//                    width = matchParent
//                }

                textView {
                    text = "退出登录"
                    textSize = 14f
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    gravity = Gravity.CENTER
                    this.withTrigger().click  {

                        if(token != ""){
                            thisDialog = DialogUtils.showLoading(this@SystemSettingsActivity)
                            mHandler.postDelayed(r, 12000)
                            GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                                loginout()
                            }
                        }else{
                            val toast = Toast.makeText(applicationContext, "请先登录账号", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            startActivity<StartActivity>()
                            overridePendingTransition(
                                R.anim.right_in,
                                R.anim.left_out
                            )
                        }
                    }
                }.lparams() {
                    height = dip(60)
                    width = matchParent
                }


            }.lparams() {
                topMargin = dip(20)

                height = wrapContent
                width = matchParent
            }
        }
    }


    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@SystemSettingsActivity, 0, toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        toolbar1.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(
                R.anim.left_in,
                R.anim.right_out
            )
        }
    }

    private suspend fun loginout(){
        try {
            val retrofitUils = RetrofitUtils(this@SystemSettingsActivity, "https://apass.sklife.jp/")
            val it = retrofitUils.create(SettingApi::class.java)
                .logout("MOBILE")
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {

//                DialogUtils.hideLoading(thisDialog)
                val sp = PreferenceManager.getDefaultSharedPreferences(this@SystemSettingsActivity).edit()
                sp.remove("token")
//                sp.remove("userName")
                sp.remove("MyRoomNum")
                sp.remove("MyRoomName")
                sp.remove("nickName")
                sp.remove("level")
                sp.commit()

                DialogUtils.hideLoading(thisDialog)
                startActivity<StartActivity>()
                overridePendingTransition(
                    R.anim.fade_in_out,
                    R.anim.fade_in_out
                )
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
        }
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId =
            context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            var scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
    }

}
