package com.example.facetime.login.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import click
import com.alibaba.fastjson.JSON
import com.example.facetime.R
import com.example.facetime.login.api.RegisterApi
import com.example.facetime.util.DialogUtils
import com.example.facetime.util.MimeType
import com.example.facetime.util.MyDialog
import com.example.facetime.util.RetrofitUtils
import com.jaeger.library.StatusBarUtil
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.HttpException
import java.util.regex.Pattern
import withTrigger

class RegisterSetPassword : AppCompatActivity() {

    private lateinit var passwordFirst: EditText
    private lateinit var passwordAgain: EditText
    private lateinit var toolbar1: Toolbar
    var phone = ""
    var country = ""
    var verifyCode = ""
    var thisDialog: MyDialog? = null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!) {
            val toast = Toast.makeText(
                this@RegisterSetPassword,
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

        if(intent.getStringExtra("phone")!=null){
            phone = intent.getStringExtra("phone") as String
        }
        if(intent.getStringExtra("country")!=null){
            country = intent.getStringExtra("country") as String
        }
        if(intent.getStringExtra("verifyCode")!=null){
            verifyCode = intent.getStringExtra("verifyCode") as String
        }

        frameLayout {
            backgroundColor = Color.WHITE
            setOnClickListener {
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
                    height = dip(65 - getStatusBarHeight(this@RegisterSetPassword))
                    topMargin = dip(getStatusBarHeight(this@RegisterSetPassword))
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }
            linearLayout {
                orientation = LinearLayout.VERTICAL
                textView {
                    text = "设置密码"
                    textSize = 21f
                    typeface = Typeface.DEFAULT_BOLD
                    textColor = Color.BLACK
                }.lparams(wrapContent, wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                //8-16位  字母数字符号，至少任意两种
                relativeLayout {
                    backgroundResource = R.drawable.border
                    passwordFirst = editText {
                        hint = "请输入密码"
                        singleLine = true
                        padding = dip(5)
                        setHintTextColor(Color.GRAY)
                        textColor = Color.BLACK
                        backgroundColor = Color.TRANSPARENT
                        inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

                        setOnKeyListener(object: View.OnKeyListener{
                            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                                if(event!=null){
                                    if(event.action ==KeyEvent.ACTION_UP){
                                        val bool = pwdMatch(passwordFirst.text.toString())
                                        if(bool){
                                            val rela = passwordFirst.parent as RelativeLayout
                                            rela.backgroundResource = R.drawable.border
                                            return true
                                        }
                                    }
                                }
                                return false
                            }
                        })
                    }.lparams(matchParent, matchParent)
                }.lparams(matchParent, dip(55)) {
                    topMargin = dip(15)
                }
                relativeLayout {
                    backgroundResource = R.drawable.border
                    passwordAgain = editText {
                        hint = "请再次输入密码"
                        singleLine = true
                        padding = dip(5)
                        backgroundColor = Color.TRANSPARENT
                        setHintTextColor(Color.GRAY)
                        textColor = Color.BLACK
                        inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        setOnKeyListener(object : View.OnKeyListener {
                            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                                if (event != null) {
                                    if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.action) {
                                        //处理事件
                                        clearFocus()
                                        closeFocusjianpan()
                                        return true
                                    }
                                    if(event.action ==KeyEvent.ACTION_UP){
                                        if (passwordAgain.text.toString() == passwordFirst.text.toString()) {
                                            val rela = passwordAgain.parent as RelativeLayout
                                            rela.backgroundResource = R.drawable.border
                                            return true
                                        }
                                    }
                                }
                                return false
                            }
                        })
                    }.lparams(matchParent, matchParent)
                }.lparams(matchParent, dip(55)) {
                    topMargin = dip(15)
                }
                button {
                    gravity = Gravity.CENTER
                    text = "下一步"
                    textSize = 16f
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    this.withTrigger().click  {

                    closeFocusjianpan()
                        if (passwordFirst.text.toString() == "") {
                            val rela = passwordFirst.parent as RelativeLayout
                            rela.backgroundResource = R.drawable.input_error_border
                            toast("请输入密码")
                            return@click
                        }
                        if (!pwdMatch(passwordFirst.text.toString())) {
                            val rela = passwordFirst.parent as RelativeLayout
                            rela.backgroundResource = R.drawable.input_error_border
                            toast("密码格式不正确")
                            return@click
                        }
                        if (passwordAgain.text.toString() == "") {
                            val rela = passwordAgain.parent as RelativeLayout
                            rela.backgroundResource = R.drawable.input_error_border
                            toast("请再次输入密码")
                            return@click
                        }
                        if (passwordFirst.text.toString() != passwordAgain.text.toString()) {
                            val rela = passwordAgain.parent as RelativeLayout
                            rela.backgroundResource = R.drawable.input_error_border
                            toast("两次密码不匹配")
                            return@click
                        }
                        thisDialog = DialogUtils.showLoading(this@RegisterSetPassword)
                        mHandler.postDelayed(r, 12000)
                        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                            registerUser()
                        }
                    }
                }.lparams(matchParent, dip(50)) {
                    topMargin = dip(40)
                }
            }.lparams(matchParent, wrapContent) {
                setMargins(dip(15), dip(150), dip(15), 0)
            }
        }
    }

    private fun pwdMatch(text: String): Boolean{
        val patter = Pattern.compile("^(?![0-9]+\$)(?![a-z]+\$)(?![A-Z]+\$)(?![,\\.#%'\\+\\*\\-:;^_`]+\$)[,\\.#%'\\+\\*\\-:;^_`0-9A-Za-z]{8,16}\$")
        val match = patter.matcher(text)
        return match.matches()
    }

    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@RegisterSetPassword, 0, toolbar1)
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

    private suspend fun registerUser(){
        try{
            val params = HashMap<String, String>()
            params["country"] = country
            params["username"] = phone
            params["code"] = verifyCode
            params["password"] = passwordFirst.text.toString()
            params["system"] = "SK"
            params["deviceType"] = "ANDROID"


            val userJson = JSON.toJSONString(params)

            val body = RequestBody.create(MimeType.APPLICATION_JSON,userJson)
            val retrofitUils =
                RetrofitUtils(this@RegisterSetPassword, "https://apass.sklife.jp/")
            val it = retrofitUils.create(RegisterApi::class.java)
                .userRegister(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()
            if (it.code() in 200..299) {
                DialogUtils.hideLoading(thisDialog)
                val toast =
                    Toast.makeText(applicationContext, "注册成功", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

                startActivity<RegisterSetNickName>()
                overridePendingTransition(
                    R.anim.right_in,
                    R.anim.left_out
                )
            }
            DialogUtils.hideLoading(thisDialog)
        }catch (throwable: Throwable){
            if(throwable is HttpException){
                println(throwable.message())
            }
            DialogUtils.hideLoading(thisDialog)
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

    private fun closeFocusjianpan() {
        //关闭ｅｄｉｔ光标
        passwordFirst.clearFocus()
        passwordAgain.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(passwordFirst.windowToken, 0)
        val code = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        code.hideSoftInputFromWindow(passwordAgain.windowToken, 0)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event != null) {
            if(keyCode == KeyEvent.KEYCODE_BACK ){
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