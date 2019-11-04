package com.example.facetime.conference

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import com.example.facetime.login.StartActivity
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.sahooz.library.Country
import com.sahooz.library.PickActivity
import org.jetbrains.anko.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var isChoose: CheckBox
    private lateinit var countryCode: TextView
    private lateinit var phoneNum: EditText
    private lateinit var vcodeNum: EditText
    var isPhoneFormat = false
    lateinit var codeText: TextView
    private var runningDownTimer: Boolean = false

    @SuppressLint("RtlHardcoded", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {
            backgroundColor = Color.TRANSPARENT
            setOnClickListener {
                closeFocusjianpan()
            }
            linearLayout {
                gravity = Gravity.RIGHT
                textView {
                    gravity = Gravity.CENTER
                    text = "登录"
                    textSize = 14f
                    setOnClickListener {
                        val intent = Intent(this@RegisterActivity, StartActivity::class.java)
                        startActivity(intent)
                        finish()
                        overridePendingTransition(R.anim.left_in, R.anim.right_out)
                    }
                }.lparams(dip(30), matchParent) {
                    rightMargin = dip(15)
                }
            }.lparams(matchParent, dip(45)) {
                topMargin = dip(15)
            }
            linearLayout {
                orientation = LinearLayout.VERTICAL
                textView {
                    text = "注册"
                    textSize = 23f
                }.lparams(wrapContent, wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                linearLayout {
                    backgroundColor = Color.WHITE
                    orientation = LinearLayout.HORIZONTAL
                    countryCode = textView {
                        gravity = Gravity.CENTER
                        text = "+86"
                        textSize = 14f
                        setOnClickListener {
                            startActivityForResult(
                                Intent(
                                    applicationContext,
                                    PickActivity::class.java
                                ), 111
                            )
                        }
                    }.lparams(dip(50), matchParent)
                    phoneNum = editText {
                        hint = "请输入手机号码"
                        background = null
                        maxLines = 1
                    }.lparams(wrapContent, matchParent) {
                        weight = 1f
                        rightMargin = dip(10)
                    }
                }.lparams(matchParent, dip(45)) {
                    topMargin = dip(15)
                }
                linearLayout {
                    backgroundColor = Color.WHITE
                    orientation = LinearLayout.HORIZONTAL
                    vcodeNum = editText {
                        hint = "请输入验证码"
                        background = null
                        maxLines = 1
                    }.lparams(wrapContent, matchParent) {
                        weight = 1f
                        leftMargin = dip(10)
                    }
                    codeText = textView {
                        gravity = Gravity.CENTER
                        text = "获取"
                        textSize = 14f
                        setOnClickListener {
                            closeFocusjianpan()
                            if (phoneNum.text.toString() != "") {
                                val phone = countryCode.text.toString() + phoneNum.text.toString()
                                isPhoneFormat =
                                    isPhoneNumberValid(
                                        phone,
                                        countryCode.text.toString().substring(1)
                                    )
                                if (isPhoneFormat) {
                                    onPcode()
                                } else {
                                    toast("手机号格式错误")
                                }
                            } else {
                                toast("请输入手机号")
                            }
                        }
                    }.lparams(dip(60), matchParent)
                }.lparams(matchParent, dip(45)) {
                    topMargin = dip(15)
                }
                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    isChoose = checkBox {}.lparams(wrapContent, wrapContent)
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        setOnClickListener {
                            isChoose.isChecked = !isChoose.isChecked
                        }
                        textView {
                            text = "我同意"
                        }
                        textView {
                            text = "隐私协议"
                            textColor = Color.BLUE
                            setOnClickListener {
                                toast("隐私协议")
                            }
                        }
                        textView {
                            text = "和"
                        }
                        textView {
                            text = "服务声明"
                            textColor = Color.BLUE
                            setOnClickListener {
                                toast("服务声明")
                            }
                        }
                    }
                }.lparams(matchParent, wrapContent) {
                    topMargin = dip(20)
                }
                button {
                    text = "下一步"
                    textColor = Color.WHITE
                    backgroundColor = Color.parseColor("#00BFFF")
                    setOnClickListener {
                        val phone = countryCode.text.toString() + phoneNum.text.toString()
                        isPhoneFormat =
                            isPhoneNumberValid(
                                phone,
                                countryCode.text.toString().substring(1)
                            )
                        if (phoneNum.text.toString() == "" || !isPhoneFormat) {
                            toast("手机号为空或格式不对")
                        } else {
                            if (vcodeNum.text.toString() == "") {
                                toast("验证码为空")
                            } else {
                                if (isChoose.isChecked) {
                                    startActivity<RegisterSetPassword>()
                                } else {
                                    toast("请勾选协议")
                                }
                            }
                        }
                    }
                }.lparams(matchParent, dip(50))
            }.lparams(matchParent, dip(500)) {
                setMargins(dip(15), dip(150), dip(15), 0)
            }
        }
    }

    private fun closeFocusjianpan() {
        //关闭ｅｄｉｔ光标
        phoneNum.clearFocus()
        vcodeNum.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(phoneNum.windowToken, 0)
        val code = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        code.hideSoftInputFromWindow(vcodeNum.windowToken, 0)
    }

    //发送验证码按钮
    fun onPcode() {
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
            codeText.text = (l / 1000).toString() + "s"
            codeText.setOnClickListener { toast("冷却中...") }
        }

        override fun onFinish() {
            runningDownTimer = false
            codeText.text = "获取"
            codeText.setOnClickListener {
                onPcode()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            val country = Country.fromJson(data!!.getStringExtra("country"))
            val areeaCode = "+" + country.code
            countryCode.text = areeaCode
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
}