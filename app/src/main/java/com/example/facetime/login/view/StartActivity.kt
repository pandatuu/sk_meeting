package com.example.facetime.login.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import com.example.facetime.conference.view.MenuActivity
import com.example.facetime.setting.view.ReadSetPasswordActivity
import com.example.facetime.login.fragment.UserAgreement
import com.example.facetime.register.view.RegisterActivity
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.jaeger.library.StatusBarUtil
import com.sahooz.library.Country
import com.sahooz.library.PickActivity
import org.jetbrains.anko.*


class StartActivity : AppCompatActivity() {
    private lateinit var telePhone: EditText
    lateinit var password: EditText
    private lateinit var phoneNumber: TextView
    private lateinit var isChoose: CheckBox
    lateinit var saveTool: SharedPreferences
    private var exitTime: Long = 0
    private lateinit var toolbar1: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        saveTool = PreferenceManager.getDefaultSharedPreferences(this@StartActivity)
        frameLayout {
            backgroundColor = Color.parseColor("#F2F2F2")
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
                                R.anim.fade_in_out,
                                R.anim.fade_in_out
                            )
                        }
                        text="返回"
                        gravity=Gravity.CENTER

                    }.lparams(){
                        height= matchParent
                        width= wrapContent
                    }
                    relativeLayout {
                        textView {
                            text = "注册"
                            gravity = Gravity.RIGHT
                            textSize = 16f
                            textColor = Color.parseColor("#7F7F7F")
                            setOnClickListener {
                                startActivity<RegisterActivity>()
                                overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }
                        }.lparams(height = wrapContent, width = matchParent){
                            centerVertically()
                            alignParentRight()
                            rightMargin = dip(20)
                        }
                    }.lparams(dip(0),matchParent){
                        weight = 1f
                    }
                }.lparams() {
                    weight = 1f
                    width = dip(0)
                    height = dip(65 - getStatusBarHeight(this@StartActivity))
                    topMargin=dip(getStatusBarHeight(this@StartActivity))
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }
            verticalLayout {
                setOnClickListener {
                    closeFocusjianpan()
                }

                textView {
                    text = "登录"
                    gravity = Gravity.CENTER
                    typeface = Typeface.DEFAULT_BOLD
                    textSize = 21f
                    typeface = Typeface.DEFAULT_BOLD
                    textColor = Color.BLACK
                }.lparams(height = wrapContent, width = matchParent) {
                    topMargin = dip(75)
                }

                linearLayout {
                    backgroundResource = R.drawable.border
                    orientation= LinearLayout.HORIZONTAL

                    phoneNumber = textView {
                        text = "+86"
                        gravity = Gravity.CENTER
                        backgroundColor = Color.TRANSPARENT
                        textSize = 15f
                        textColor = Color.BLACK
                        typeface = Typeface.DEFAULT_BOLD
                        setOnClickListener {
                            startActivityForResult(
                                Intent(
                                    applicationContext,
                                    PickActivity::class.java
                                ), 111
                            )
                        }
                    }.lparams(height = matchParent, width = wrapContent) {
                        margin=dip(1)
                        leftMargin=dip(10)
                        rightMargin = dip(10)
                    }

                    telePhone = editText {
                        hint = "请输入手机号码"
                        backgroundColor = Color.TRANSPARENT
                        setHintTextColor(Color.GRAY)
                        singleLine=true
                        setOnKeyListener(object : View.OnKeyListener{
                            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                                if (event != null) {
                                    if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.action) {
                                        //处理事件
//                                        password.requestFocus()
                                        return true
                                    }
                                }
                                return false
                            }
                        })
                        setOnFocusChangeListener { view, b ->
                            if (b) {
                                setHintTextColor(Color.BLACK)
                            } else {
                                setHintTextColor(Color.GRAY)
                            }
                        }
                    }.lparams(width = dip(1), height = matchParent) {
                        weight = 1f
                        margin=dip(1)
                    }
                }.lparams(height = dip(50), width = matchParent) {
                    topMargin = dip(25)
                }

                linearLayout {
                    backgroundResource = R.drawable.border

                    password = editText {
                        hint = "请输入密码"
                        backgroundColor = Color.TRANSPARENT
                        singleLine=true
                        inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        setHintTextColor(Color.GRAY)

                       transformationMethod =    PasswordTransformationMethod()

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
                        setOnFocusChangeListener { view, b ->
                            if (b) {
                                setHintTextColor(Color.BLACK)
                            } else {
                                setHintTextColor(Color.GRAY)
                            }
                        }
                    }.lparams(width = matchParent, height = matchParent) {
                        leftMargin=dip(10)
                    }
                }.lparams(height = dip(50), width = matchParent) {

                    topMargin = dip(10)
                    leftPadding = dip(10)
                    rightPadding = dip(10)
                }

                linearLayout {
                    setOnClickListener {
                        isChoose.isChecked = !isChoose.isChecked
                    }

                    isChoose = checkBox {
                    }

                    textView {
                        text = "我同意"
                    }
                    textView {
                        text = "隐私协议"
                        textColor = Color.parseColor("#219ad5")
                        setOnClickListener {
                            startActivity<UserAgreement>()
                            overridePendingTransition(R.anim.right_in, R.anim.left_out)
                        }
                    }
                    textView {
                        text = "和"
                    }
                    textView {
                        text = "服务声明"
                        textColor = Color.parseColor("#219ad5")
                        setOnClickListener {
                            startActivity<ServiceStatement>()
                            overridePendingTransition(R.anim.right_in, R.anim.left_out)
                        }
                    }
                }.lparams {
                    topMargin = dip(15)
                }


                textView {
                    backgroundResource = R.drawable.bottonbg
                    text = "登录"
                    textColor = Color.WHITE
                    textSize = 16f
                    gravity = Gravity.CENTER
                    setOnClickListener {
                        submit()
                    }
                }.lparams(width = matchParent,height = dip(50)) {
                    topMargin = dip(30)
                }

                textView {
                    text = "忘记密码"
                    textColor = Color.parseColor("#7F7F7F")
                    gravity = Gravity.RIGHT
                    setOnClickListener {
                        startActivity<ReadSetPasswordActivity>()
                        this@StartActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                    }
                }.lparams(height = wrapContent, width = matchParent) {
                    topMargin = dip(25)
                }

            }.lparams(width = matchParent, height = matchParent) {
                leftMargin = dip(20)
                rightMargin = dip(20)
                topMargin = dip(65)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@StartActivity, 0, toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        toolbar1.setNavigationOnClickListener {
            startActivity<MenuActivity>()
            overridePendingTransition(
                R.anim.fade_in_out,
                R.anim.fade_in_out
            )
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
        code.hideSoftInputFromWindow(password.windowToken, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            val country = Country.fromJson(data!!.getStringExtra("country"))
            val areeaCode = "+" + country.code
            phoneNumber.text = areeaCode
        }
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


    private fun submit() {
        val countryCode = phoneNumber.text.toString().trim()
        val phone = telePhone.text.toString().trim()
        val myPassword = password.text.toString().trim()
        val country = countryCode.substring(1, 3)
        val myPhone = countryCode + phone
        val result = isPhoneNumberValid(myPhone, country)
        val myCheck = isChoose.isChecked

        if (!myCheck) {
            toast("请勾选协议")
            return
        }

        if (phone.isEmpty()) {
            toast("请输入手机号")
            return
        }

        if (myPassword.isEmpty()) {
            toast("请输入密码")
            return
        }

        // 电话判定,测试阶段屏蔽
        if (!result) {
            toast("请输入正确的手机号")
            return
        }

        toast("$phone,$myPassword")


        val mEditor: SharedPreferences.Editor = saveTool.edit()
        mEditor.putString("token", "login")
        mEditor.putString("userName", "testName")

        var str=getNum()
        mEditor.putString("MyRoomNum", str)

        val i = Intent(this, MenuActivity::class.java)
        startActivity(i)
        overridePendingTransition(R.anim.fade_in_out, R.anim.fade_in_out)
        mEditor.apply()
    }

    fun getNum():String{
        var result=""
        var d=0
        while(true){
            val random=(Math.random()*1000).toInt()
            d=random%9
            if(d>3){
             break
            }
        }

        for(i in 0 until d){
            var c=""
            while(true){
                var r=Math.ceil(Math.random()*1000).toInt()%122
                if((r>=48 && r<=57)  || (r>=65 && r<=90)  || (r>=97 && r<=122) ){
                    c= r.toChar().toString()
                    break
                }
            }
            result=result+c
        }

        return result
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event != null) {
            if(keyCode == KeyEvent.KEYCODE_BACK ){
                startActivity<MenuActivity>()
                overridePendingTransition(
                    R.anim.left_in,
                    R.anim.right_out
                )
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}