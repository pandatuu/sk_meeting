package com.example.facetime.login

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class StartActivity : AppCompatActivity(){
    lateinit var telePhone:EditText
    lateinit var password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        linearLayout {
            backgroundColor = Color.parseColor("#F2F2F2")
           verticalLayout{
               backgroundColor = Color.parseColor("#F2F2F2")
               onClick {
                   closeFocusjianpan()
               }
                textView {
                    text = "注册"
                    gravity = Gravity.RIGHT
                    textSize = 16f
                    textColor = Color.parseColor("#7F7F7F")
                }.lparams(height = wrapContent,width = matchParent){
                    topPadding = dip(20)
                }

               textView {
                   text = "登录"
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

                   telePhone = editText {
                       hint = "请输入手机号码"
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

                   password = editText {
                       hint = "请输入密码"
                       backgroundColor = Color.WHITE
                   }.lparams(width = matchParent,height = wrapContent){
                       weight = 1f
                   }
               }.lparams(height = wrapContent,width = matchParent){

                   topMargin = dip(10)
                   leftPadding = dip(10)
                   rightPadding = dip(10)
               }

               linearLayout {
                   checkBox {
                   }

                   textView {
                       text = "我同意"
                   }
                   textView {
                       text = "隐私协议"
                       textColor = Color.parseColor("#44CDF6")
                   }
                   textView {
                       text = "和"
                   }
                   textView {
                       text = "服务声明"
                       textColor = Color.parseColor("#44CDF6")
                   }
               }.lparams(){
                   topMargin = dip(15)
               }


               button {
                   backgroundResource = R.drawable.login_button
                   text = "登录"
                   textColor = Color.WHITE
                   textSize = 21f
               }.lparams(width = matchParent,height = wrapContent){
                   topMargin = dip(30)
               }

               textView {
                   text = "忘记密码"
                   textColor = Color.parseColor("#7F7F7F")
                   gravity = Gravity.RIGHT
                   onClick {
                       startActivity<ReadSetPasswordActivity>()
                   }
               }.lparams(height = wrapContent,width = matchParent){
                   topMargin = dip(25)
               }

           }.lparams(width = matchParent,height = matchParent){
                leftMargin = dip(20)
                rightMargin = dip(20)
           }
        }
    }

    private fun closeFocusjianpan() {
        //关闭ｅｄｉｔ光标
        telePhone.clearFocus()
        password.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(telePhone.windowToken, 0)
        val code = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        code.hideSoftInputFromWindow(password!!.windowToken, 0)
    }
}