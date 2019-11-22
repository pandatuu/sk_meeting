package com.example.facetime.login.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import click
import com.alibaba.fastjson.JSON
import com.example.facetime.R
import com.example.facetime.login.api.LoginApi
import com.example.facetime.util.DialogUtils
import com.example.facetime.util.MimeType
import com.example.facetime.util.MyDialog
import com.example.facetime.util.RetrofitUtils
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.jaeger.library.StatusBarUtil
import com.sahooz.library.Country
import com.sahooz.library.PickActivity
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.HttpException
import withTrigger

class ReadSetPasswordActivity : AppCompatActivity() {
    private var runningDownTimer: Boolean = false
    lateinit var timeButton: TextView
    lateinit var telephone: EditText
    lateinit var myCode: EditText
    lateinit var password: EditText
    lateinit var phoneNumber: TextView
    private lateinit var toolbar1: Toolbar
    private var sendBool = false
    var thisDialog: MyDialog? = null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!) {
            val toast = Toast.makeText(
                this@ReadSetPasswordActivity,
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

        verticalLayout {
            backgroundColor = Color.parseColor("#f2f2f2")
            this.withTrigger().click  {
                closeFocusjianpan()
            }

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



            verticalLayout {
                textView {
                    text = "重置密码"
                    gravity = Gravity.CENTER
                    textSize = 21f
                    typeface = Typeface.DEFAULT_BOLD
                    textColor = Color.BLACK
                }.lparams(height = wrapContent, width = matchParent) {
                    topMargin = dip(75)
                }

                linearLayout {
                    backgroundResource = R.drawable.border
                    orientation = LinearLayout.HORIZONTAL

                    phoneNumber = textView {
                        text = "+86"
                        gravity = Gravity.CENTER
                        backgroundColor = Color.TRANSPARENT
                        textSize = 15f
                        textColor = Color.BLACK
                        typeface = Typeface.DEFAULT_BOLD

                        this.withTrigger().click  {
                            startActivityForResult(
                                Intent(
                                    applicationContext,
                                    PickActivity::class.java
                                ), 111
                            )
                        }
                    }.lparams(height = matchParent, width = wrapContent) {
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                    }

                    telephone = editText {
                        hint = "请输入手机号码"
                        backgroundColor = Color.TRANSPARENT
                        setHintTextColor(Color.GRAY)
                        textColor = Color.BLACK
                        singleLine = true
                        setOnKeyListener(object : View.OnKeyListener {
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

                        setOnFocusChangeListener { view, b ->
                            if (b) {
                                setHintTextColor(Color.BLACK)
                            } else {
                                setHintTextColor(Color.GRAY)
                            }
                        }
                    }.lparams(width = matchParent, height = matchParent) {
                        margin = dip(1)
                        weight = 1f
                    }
                }.lparams(height = dip(50), width = matchParent) {
                    topMargin = dip(25)
                }

                linearLayout {
                    backgroundResource = R.drawable.border

                    myCode = editText {
                        hint = "请输入验证码"
                        backgroundColor = Color.TRANSPARENT
                        setHintTextColor(Color.GRAY)
                        textColor = Color.BLACK
                        singleLine = true
                        setOnKeyListener(object : View.OnKeyListener {
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
                    }.lparams(width = dip(0), height = matchParent) {
                        weight = 1f
                        leftMargin = dip(10)
                    }

                    timeButton = textView {
                        gravity = Gravity.CENTER
                        text = "获取"
                        textSize = 14f
                        typeface = Typeface.DEFAULT_BOLD
                        textColor = Color.BLACK
                        this.withTrigger().click  {
                            val result = determinePhone()
                            if (result) {
                                thisDialog = DialogUtils.showLoading(this@ReadSetPasswordActivity)
                                mHandler.postDelayed(r, 12000)
                                GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                                    sendBool = sendVerificationCode(
                                        telephone.text.toString().trim(),
                                        phoneNumber.text.toString().substring(1)
                                    )
                                    if (sendBool){
                                        DialogUtils.hideLoading(thisDialog)
                                        onPcode()
                                    }
                                }
                            } else {
                                toast("请输入正确的手机号")
                            }
                        }
                    }.lparams(width = dip(60), height = matchParent)

                }.lparams(height = dip(50), width = matchParent) {
                    topMargin = dip(10)
                }
                linearLayout {
                    backgroundResource = R.drawable.border

                    password = editText {
                        hint = "请输入新密码"
                        backgroundColor = Color.TRANSPARENT
                        setHintTextColor(Color.GRAY)
                        textColor = Color.BLACK
                        singleLine = true
                        setOnKeyListener(object : View.OnKeyListener {
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
                    }.lparams(width = dip(0), height = matchParent) {
                        weight = 1f
                        leftMargin = dip(10)
                    }
                }.lparams(height = dip(50), width = matchParent) {
                    topMargin = dip(10)
                }

                textView {
                    text = "下一步"
                    textSize = 16f
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    gravity = Gravity.CENTER
                    this.withTrigger().click  {
                        next()
                    }
                }.lparams(width = matchParent, height = dip(50)) {
                    topMargin = dip(50)
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

    /**
     * 倒计时
     */
    private val downTimer = object : CountDownTimer((60 * 1000).toLong(), 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(l: Long) {
            runningDownTimer = true
            timeButton.text = (l / 1000).toString() + "s"
            timeButton.withTrigger().click  {
                toast("冷却中...")
            }


        }

        override fun onFinish() {
            runningDownTimer = false
            timeButton.text = "发送"

            timeButton.withTrigger().click  {
                onPcode()
            }

        }
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId =
            context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
            val scale = context.getResources().getDisplayMetrics().density;
            result = ((result / scale + 0.5f).toInt());
        }
        return result
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
        password.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(telephone.windowToken, 0)
        val code = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        code.hideSoftInputFromWindow(myCode.windowToken, 0)
        val mypassword = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mypassword.hideSoftInputFromWindow(password.windowToken, 0)
    }

    fun determinePhone(): Boolean {
        val countryCode = phoneNumber.text.toString().trim()
        val phone = telephone.text.toString().trim()
        val country = countryCode.substring(1)
        val myPhone = countryCode + phone
        val result = isPhoneNumberValid(myPhone, country)

        if (result) {
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

    fun next() {
        thisDialog = DialogUtils.showLoading(this@ReadSetPasswordActivity)
        mHandler.postDelayed(r, 12000)
        val res = determinePhone()
        val phone = telephone.text.toString().trim()
        val code = myCode.text.toString().trim()
        val mypassword = password.text.toString().trim()

        if (phone.isNullOrEmpty()) {
            toast("请输入手机号码")
            return
        }

        if (code.isNullOrEmpty()) {
            toast("请输入验证码")
            return
        }
        if (mypassword.isNullOrEmpty()) {
            toast("请输入新密码")
            return
        }

        if (!res) {
            toast("请输入正确的手机号码")
            return
        }
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            //更新密码
            updatePassword(phoneNumber.text.toString().substring(1), phone, code, mypassword)
        }
    }

    //发送验证码
    private suspend fun sendVerificationCode(phoneNum: String, country: String): Boolean {
        try {
            val deviceModel: String = Build.MODEL
            val manufacturer: String = Build.BRAND
            val params = mapOf(
                "phone" to phoneNum,
                "country" to country,
                "deviceType" to "ANDROID",
                "codeType" to "LOGIN",
                "manufacturer" to manufacturer,
                "deviceModel" to deviceModel
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils =
                RetrofitUtils(this@ReadSetPasswordActivity, "https://apass.sklife.jp/")
            val it = retrofitUils.create(LoginApi::class.java)
                .sendvCode(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
//                DialogUtils.hideLoading(thisDialog)
                val toast =
                    Toast.makeText(applicationContext, "認証コードは既に送信されました。", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return true
            }
            if (it.code() == 409) {
//                DialogUtils.hideLoading(thisDialog)
                val toast =
                    Toast.makeText(applicationContext, "この携帯番号は既に登録されました。", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return false
            }
            return false
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }
//            DialogUtils.hideLoading(thisDialog)
            return false
        }
    }

    private suspend fun updatePassword(
        country: String,
        phone: String,
        code: String,
        password: String
    ) {
        try {
            val params = mapOf(
                "country" to country,
                "phone" to phone,
                "code" to code,
                "password" to password
            )


            val userJson = JSON.toJSONString(params)

            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            var retrofitUils = RetrofitUtils(this@ReadSetPasswordActivity, "https://apass.sklife.jp/")

            val it = retrofitUils.create(LoginApi::class.java)
                .findPassword(body)
                .subscribeOn(Schedulers.io()) //观察者 切换到主线程
                .awaitSingle()

            if (it.code() in 200..299) {
                DialogUtils.hideLoading(thisDialog)

                startActivity<StartActivity>()
                this@ReadSetPasswordActivity.overridePendingTransition(
                    R.anim.right_in,
                    R.anim.left_out
                )
            }
            if(it.code() == 406){
                println("参数错误")
            }
            DialogUtils.hideLoading(thisDialog)
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
            }

            DialogUtils.hideLoading(thisDialog)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event != null) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                finish()
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