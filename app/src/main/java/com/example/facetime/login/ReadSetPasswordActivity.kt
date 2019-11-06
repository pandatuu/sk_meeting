package com.example.facetime.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.jaeger.library.StatusBarUtil
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
                    toolbar1 = toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams(){
                        width = dip(9)
                        height = dip(65 - getStatusBarHeight(this@ReadSetPasswordActivity))
                        rightMargin = dip(15)

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
                            text = "返回"
                            gravity = Gravity.CENTER

                        }.lparams() {
                            height = matchParent
                            width = wrapContent
                        }
                    }.lparams() {
                        weight = 1f
                        width = dip(0)
                        height = dip(65 - getStatusBarHeight(this@ReadSetPasswordActivity))
                        topMargin = dip(getStatusBarHeight(this@ReadSetPasswordActivity))
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(65)
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
                        setOnKeyListener(object : View.OnKeyListener{
                            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                                if (event != null) {
                                    if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.action) {
                                        //处理事件
                                        myCode.requestFocus()
                                        return true
                                    }
                                }
                                return false
                            }
                        })
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
                        setOnKeyListener(object : View.OnKeyListener{
                            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                                if (event != null) {
                                    if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.action) {
                                        //处理事件
                                        clearFocus()
                                        closeFocusjianpan()
                                        return true
                                    }
                                }
                                return false
                            }
                        })
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
                    backgroundResource = R.drawable.bottonbg
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
    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@ReadSetPasswordActivity, 0, toolbar1)
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
    //发送验证码按钮
    private fun onPcode() {

        //如果60秒倒计时没有结束
        if (runningDownTimer) {
            return
        }

        downTimer.start()  // 倒计时开始

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
        this@ReadSetPasswordActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }
}