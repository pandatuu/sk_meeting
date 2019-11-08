package com.example.facetime.conference


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

import com.facebook.react.modules.core.PermissionListener
import org.jitsi.meet.sdk.*
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import com.example.facetime.R
import com.example.facetime.login.StartActivity
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext
import org.jetbrains.anko.sdk25.coroutines.onClick


open class SystemSettingsActivity : AppCompatActivity() {


    private lateinit var toolbar1: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                        setOnClickListener {
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
                    setOnClickListener {
                        startActivity<UpdateNickName>()
                        overridePendingTransition(
                            R.anim.right_in,
                            R.anim.left_out
                        )
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
                    setOnClickListener {
                        startActivity<UpdatePassword>()
                        overridePendingTransition(
                                    R.anim.right_in,
                                    R.anim.left_out
                                )
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
                    text = "修改服务器地址"
                    textSize = 14f
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    gravity = Gravity.CENTER
                    setOnClickListener {
                        startActivity<SetServiceAddr>()
                        overridePendingTransition(
                            R.anim.right_in,
                            R.anim.left_out
                        )
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
                    text = "退出登录"
                    textSize = 14f
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    gravity = Gravity.CENTER
                    setOnClickListener {

                        val sp = PreferenceManager.getDefaultSharedPreferences(this@SystemSettingsActivity).edit()
                        sp.remove("token")
                        sp.remove("userName")
                        sp.remove("MyRoomNum")
                        sp.remove("MyRoomName")
                        sp.commit()
                        startActivity<MenuActivity>()
                        overridePendingTransition(
                            R.anim.fade_in_out,
                            R.anim.fade_in_out
                        )
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
