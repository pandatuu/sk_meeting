package com.example.facetime.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.sahooz.library.Country
import com.sahooz.library.PickActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ReadSetPasswordActivity : AppCompatActivity(){
    private var runningDownTimer: Boolean = false
    lateinit var timeButton:TextView
    lateinit var telephone:EditText
    lateinit var myCode:EditText
    lateinit var phoneNumber:TextView

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
                    phoneNumber = textView {
                        text = "+86"
                        gravity = Gravity.CENTER
                        backgroundColor = Color.WHITE
                        textSize = 15f
                        textColor = Color.parseColor("#333333")

                        onClick {
                            startActivityForResult(Intent(applicationContext, PickActivity::class.java), 111)
                        }
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
                        weight = 1f
                    }

                    timeButton = textView {
                        gravity = Gravity.CENTER
                        text = "获取"
                        textSize = 14f
                    }.lparams(width = dip(60),height = matchParent){

                        setOnClickListener {
                            val result = determinePhone()
                            if(result){
                                onPcode()
                            } else {
                                toast("请输入正确的手机号")
                            }
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
                        next()
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
            val result = determinePhone()
            if(result){
                runningDownTimer = true
                timeButton.text = (l / 1000).toString() + "s"
                timeButton.setOnClickListener { null }
            } else {
                toast("请输入正确的手机号")
                return
            }

        }

        override fun onFinish() {
            runningDownTimer = false
            timeButton.text = "发送"
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            val country = Country.fromJson(data!!.getStringExtra("country"))
            val areeaCode = "+" + country.code
            phoneNumber.text = areeaCode
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
        code.hideSoftInputFromWindow(myCode.windowToken, 0)
    }

    fun determinePhone(): Boolean {
        val countryCode = phoneNumber.text.toString().trim()
        val phone = telephone.text.toString().trim()
        val country = countryCode.substring(1, 3)
        val myPhone = countryCode+phone
        val result = isPhoneNumberValid(myPhone,country)

        if(result){
            return true
        }
        return false
    }

    /**
     * 根据区号判断是否是正确的电话号码
     * @param phoneNumber :带国家码的电话号码
     * @param countryCode :默认国家码
     * return ：true 合法  false：不合法
     */
    private fun isPhoneNumberValid(phoneNumber: String, countryCode: String): Boolean {

        println("isPhoneNumberValid: $phoneNumber/$countryCode")
        val phoneUtil = PhoneNumberUtil.getInstance()
        try {
            val numberProto = phoneUtil.parse(phoneNumber, countryCode)
            return phoneUtil.isValidNumber(numberProto)
        } catch (e: NumberParseException) {
            System.err.println("isPhoneNumberValid NumberParseException was thrown: $e")
        }

        return false
    }

    fun next(){
        val res = determinePhone()
        val phone = telephone.text.toString().trim()
        val code = myCode.text.toString().trim()

        if(phone.isNullOrEmpty()){
            toast("请输入手机号码")
            return
        }

        if(code.isNullOrEmpty()){
            toast("请输入验证码")
            return
        }

        if(!res){
            toast("请输入正确的手机号码")
            return
        }

        startActivity<RsetPasswordActivity>()
    }
}