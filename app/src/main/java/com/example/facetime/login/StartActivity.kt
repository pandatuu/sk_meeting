package com.example.facetime.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.icu.util.MeasureUnit
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
import com.example.facetime.conference.MenuActivity
import com.example.facetime.conference.RegisterActivity
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.jaeger.library.StatusBarUtil
import com.sahooz.library.Country
import com.sahooz.library.PickActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import com.dropbox.core.v2.teamlog.ActorLogInfo.app
import androidx.core.content.ContextCompat.getSystemService



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
        linearLayout {
            backgroundColor = Color.parseColor("#F2F2F2")
            verticalLayout {
                backgroundColor = Color.parseColor("#F2F2F2")
                onClick {
                    closeFocusjianpan()
                }
                relativeLayout() {
                    backgroundColor=Color.TRANSPARENT
                    toolbar1 = toolbar {
                        backgroundColor=Color.TRANSPARENT
                        isEnabled = true
                        title = ""
                    }.lparams() {
                        width = matchParent
                        height = dip(0)
                        alignParentBottom()

                    }
                }.lparams() {
                    width = matchParent
                    height = dip(0)
                }
                textView {
                    text = "注册"
                    gravity = Gravity.RIGHT
                    textSize = 16f
                    textColor = Color.parseColor("#7F7F7F")
                    onClick {
                        startActivity<RegisterActivity>()
                        this@StartActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                    }
                }.lparams(height = wrapContent, width = matchParent) {
                    topPadding = dip(20)
                }

                textView {
                    text = "登录"
                    gravity = Gravity.CENTER
                    textSize = 21f
                    textColor = Color.parseColor("#333333")
                }.lparams(height = wrapContent, width = matchParent) {
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
                            startActivityForResult(
                                Intent(
                                    applicationContext,
                                    PickActivity::class.java
                                ), 111
                            )
                        }
                    }.lparams(height = matchParent, width = wrapContent) {
                        leftMargin = dip(5)
                        rightMargin = dip(10)
                    }

                    telePhone = editText {
                        hint = "请输入手机号码"
                        backgroundColor = Color.WHITE
                        maxLines = 1
                        setOnKeyListener(object : View.OnKeyListener{
                            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                                if (event != null) {
                                    if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.action) {
                                        //处理事件
                                        password.requestFocus()
                                        return true
                                    }
                                }
                                return false
                            }
                        })
                    }.lparams(width = matchParent, height = wrapContent) {
                        weight = 1f
                    }
                }.lparams(height = wrapContent, width = matchParent) {
                    topMargin = dip(25)
                }

                linearLayout {
                    backgroundResource = R.drawable.input_border
                    textView {

                    }.lparams(width = wrapContent, height = matchParent) {
                        topMargin = dip(10)
                    }

                    password = editText {
                        hint = "请输入密码"
                        backgroundColor = Color.WHITE
                       transformationMethod = PasswordTransformationMethod()
                        maxLines = 1
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
                    }.lparams(width = matchParent, height = wrapContent) {
                        weight = 1f
                    }
                }.lparams(height = wrapContent, width = matchParent) {

                    topMargin = dip(10)
                    leftPadding = dip(10)
                    rightPadding = dip(10)
                }

                linearLayout {
                    onClick {
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
                        onClick {
                            startActivity<UserAgreement>()
                            this@StartActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                        }
                    }
                    textView {
                        text = "和"
                    }
                    textView {
                        text = "服务声明"
                        textColor = Color.parseColor("#219ad5")
                        onClick {
                            startActivity<ServiceStatement>()
                            this@StartActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                        }
                    }
                }.lparams {
                    topMargin = dip(15)
                }


                button {
                    backgroundResource = R.drawable.bottonbg
                    text = "登录"
                    textColor = Color.WHITE
                    textSize = 21f

                    onClick {
                        submit()
                    }
                }.lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(30)
                }

                textView {
                    text = "忘记密码"
                    textColor = Color.parseColor("#7F7F7F")
                    gravity = Gravity.RIGHT
                    onClick {
                        startActivity<ReadSetPasswordActivity>()
                        this@StartActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                    }
                }.lparams(height = wrapContent, width = matchParent) {
                    topMargin = dip(25)
                }

            }.lparams(width = matchParent, height = matchParent) {
                leftMargin = dip(20)
                rightMargin = dip(20)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@StartActivity, 0, toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
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

        val i = Intent(this, MenuActivity::class.java)

        startActivity(i)

        this@StartActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
        val mEditor: SharedPreferences.Editor = saveTool.edit()

        mEditor.putString("token", "login")
        mEditor.putString("userName", "testName")
        mEditor.putString("MyRoomNum", phone)
        mEditor.apply()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event != null) {
            if(keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN){
                if((System.currentTimeMillis()-exitTime) > 2000){
                    Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis()
                } else {
                    val startMain = Intent(Intent.ACTION_MAIN)
                    startMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startMain.addCategory(Intent.CATEGORY_HOME)
                    startActivity(startMain)
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}