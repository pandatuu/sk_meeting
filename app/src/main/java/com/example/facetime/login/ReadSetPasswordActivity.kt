package com.example.facetime.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class ReadSetPasswordActivity : AppCompatActivity(){
    private var runningDownTimer: Boolean = false
    lateinit var timeButton:Button
    lateinit var telephone:EditText
    lateinit var myCode:EditText

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
                    imageView {
                        imageResource = R.mipmap.icon_back
                    }.lparams(width = wrapContent,height = wrapContent){
                        rightMargin = dip(5)
                        gravity = Gravity.CENTER
                    }
                    textView {
                        text = "返回"
                        textSize = 16f
                        textColor = Color.parseColor("#7F7F7F")
                    }.lparams(width = wrapContent,height = wrapContent){
                        gravity = Gravity.CENTER
                    }

                    this.setOnClickListener(View.OnClickListener {
                        // TODO Auto-generated method stub
                        finish()
                    })
                }.lparams(width = matchParent,height = wrapContent){
                    topMargin = dip(20)
                    gravity = Gravity.LEFT
                }

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
                    textView {
                        text = "+81"
                        gravity = Gravity.CENTER
                        backgroundColor = Color.WHITE
                        textSize = 15f
                        textColor = Color.parseColor("#333333")
                    }.lparams(height = matchParent,width = wrapContent){
                        leftMargin = dip(5)
                        rightMargin = dip(10)
                    }

                    telephone = editText {
                        hint = "请输入手机号码"
                        backgroundColor = Color.WHITE
                    }.lparams(width = matchParent,height = wrapContent){
                        weight = 1f
                    }
                }.lparams(height = wrapContent,width = matchParent){
                    topMargin = dip(25)
                }

                linearLayout {
                    backgroundResource = R.drawable.input_right
                    textView {

                    }.lparams(width = wrapContent,height = matchParent){
                        topMargin = dip(10)
                    }

                    myCode = editText {
                        hint = "请输入验证码"
                        backgroundColor = Color.WHITE
                    }.lparams(width = matchParent,height = wrapContent){
                        weight = 4f
                    }

                    timeButton = button {
                        backgroundResource = R.drawable.get_button
                        text = "获取"
                    }.lparams(width = wrapContent,height = matchParent){
                        weight = 1f
                        setOnClickListener {
                            onPcode()
                        }
                    }

                }.lparams(height = wrapContent,width = matchParent){
                    topMargin = dip(10)
                }


                button {
                    backgroundResource = R.drawable.login_button
                    text = "下一步"
                    textColor = Color.WHITE
                    textSize = 21f

                    onClick {
                        startActivity<RsetPasswordActivity>()
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

    //发送验证码按钮
    private fun onPcode() {

        //如果60秒倒计时没有结束
        if (runningDownTimer) {
            return
        }

        downTimer.start()  // 倒计时开始

    }

    /**
     * 倒计时
     */
    private val downTimer = object : CountDownTimer((60 * 1000).toLong(), 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(l: Long) {
            runningDownTimer = true
            timeButton.text = (l / 1000).toString() + "s"
            timeButton.setOnClickListener { null }
        }

        override fun onFinish() {
            runningDownTimer = false
            timeButton.text = "发送"
        }

    }

    private fun closeFocusjianpan() {
        //关闭ｅｄｉｔ光标
        telephone.clearFocus()
        myCode.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(telephone.windowToken, 0)
        val code = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        code.hideSoftInputFromWindow(myCode!!.windowToken, 0)
    }
}