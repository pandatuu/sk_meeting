package com.example.facetime.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class RsetPasswordActivity : AppCompatActivity(){
    lateinit var password:EditText
    lateinit var confirmPassword:EditText
    private lateinit var toolbar1: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayout {
            backgroundColor = Color.parseColor("#F2F2F2")
            verticalLayout {
                backgroundColor = Color.parseColor("#F2F2F2")

                onClick {
                    closeFocusjianpan()
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.BOTTOM
                    toolbar1 = toolbar {
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams(dip(9), dip(15)){
                        rightMargin = dip(5)
                    }
                    textView {
                        text = "返回"
                        textSize = 16f
                        textColor = Color.parseColor("#7F7F7F")
                    }.lparams(width = wrapContent,height = wrapContent)

                    this.setOnClickListener(View.OnClickListener {
                        // TODO Auto-generated method stub
                        finish()
                    })
                }.lparams(width = matchParent,height = dip(55))

                textView {
                    text = "重置密码"
                    gravity = Gravity.CENTER
                    textSize = 21f
                    textColor = Color.parseColor("#333333")
                }.lparams(height = wrapContent,width = matchParent){
                    topMargin = dip(75)
                }

                linearLayout {
                    backgroundResource = R.drawable.input_border
                    password = editText {
                        hint = "请输入新密码"
                        backgroundColor = Color.WHITE
                    }.lparams(width = matchParent,height = wrapContent){
                        weight = 1f
                    }
                }.lparams(height = wrapContent,width = matchParent){
                    topMargin = dip(25)
                }

                linearLayout {
                    backgroundResource = R.drawable.input_border
                    textView {

                    }.lparams(width = wrapContent,height = matchParent){
                        topMargin = dip(10)
                    }

                    confirmPassword = editText {
                        hint = "请再次输入新密码"
                        backgroundColor = Color.WHITE
                    }.lparams(width = matchParent,height = wrapContent){
                        weight = 1f
                    }
                }.lparams(height = wrapContent,width = matchParent){
                    topMargin = dip(10)
                    leftPadding = dip(10)
                    rightPadding = dip(10)
                }


                button {
                    backgroundResource = R.drawable.login_button
                    text = "完成"
                    textColor = Color.WHITE
                    textSize = 21f

                    onClick {
                        fulfill()
                    }
                }.lparams(width = matchParent,height = wrapContent){
                    topMargin = dip(50)
                }
            }.lparams(width = matchParent,height = matchParent){
                leftMargin = dip(20)
                rightMargin = dip(20)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@RsetPasswordActivity, 0, toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }
    private fun closeFocusjianpan() {
        //关闭ｅｄｉｔ光标
        password.clearFocus()
        confirmPassword.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(password.windowToken, 0)
        val code = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        code.hideSoftInputFromWindow(confirmPassword.windowToken, 0)
    }

    private fun fulfill(){
        val myPassword = password.text.toString().trim()
        val myConfirmPassword = confirmPassword.text.toString().trim()

        if(myPassword.isNullOrEmpty()){
            toast("密码不可为空！！")
            return
        }

        if(myConfirmPassword.isNullOrEmpty()){
            toast("确认密码不可为空")
            return
        }

        if(myPassword != myConfirmPassword){
            toast("密码前后不一致")
            return
        }

        toast("完成")
    }
}