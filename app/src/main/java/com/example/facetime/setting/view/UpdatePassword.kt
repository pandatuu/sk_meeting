package com.example.facetime.setting.view

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
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSON
import com.example.facetime.R
import com.example.facetime.api.LoginApi
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
import okhttp3.RequestBody
import org.jetbrains.anko.*
import retrofit2.HttpException

class UpdatePassword : AppCompatActivity() {

    private lateinit var passwordOld: EditText
    private lateinit var passwordFirst: EditText
    private lateinit var passwordAgain: EditText
    private lateinit var toolbar1: Toolbar
    var thisDialog: MyDialog? = null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!) {
            val toast = Toast.makeText(
                this@UpdatePassword,
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

        frameLayout {
            backgroundColor = Color.TRANSPARENT
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
                    height = dip(65 - getStatusBarHeight(this@UpdatePassword))
                    topMargin = dip(getStatusBarHeight(this@UpdatePassword))
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }
            linearLayout {
                orientation = LinearLayout.VERTICAL
                textView {
                    text = "修改密码"
                    textSize = 21f
                    typeface = Typeface.DEFAULT_BOLD
                    textColor = Color.BLACK
                }.lparams(wrapContent, wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                relativeLayout {
                    backgroundResource = R.drawable.border
                    passwordOld = editText {
                        hint = "请输入旧密码"
                        singleLine = true
                        setHintTextColor(Color.GRAY)
                        textColor = Color.BLACK
                        padding = dip(5)
                        backgroundColor = Color.TRANSPARENT
                        inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    }.lparams(matchParent, matchParent)
                }.lparams(matchParent, dip(55)) {
                    topMargin = dip(15)
                }
                relativeLayout {
                    backgroundResource = R.drawable.border
                    passwordFirst = editText {
                        hint = "请输入密码"
                        singleLine = true
                        setHintTextColor(Color.GRAY)
                        textColor = Color.BLACK
                        padding = dip(5)
                        backgroundColor = Color.TRANSPARENT
                        inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
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
                    text = "完成"
                    textSize = 16f
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    setOnClickListener {
                        closeFocusjianpan()
                        if (passwordFirst.text.toString() == "") {
                            toast("请输入密码")
                        } else {
                            if (passwordAgain.text.toString() == "") {
                                toast("请再次输入密码")
                            } else {
                                if (passwordAgain.text.toString() != passwordFirst.text.toString()) {
                                    toast("两次密码不匹配")
                                } else {
                                    thisDialog = DialogUtils.showLoading(this@UpdatePassword)
                                    mHandler.postDelayed(r, 12000)
                                    GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                                        updatePassword(passwordOld.text.toString(), passwordFirst.text.toString(), passwordAgain.text.toString())
                                    }
                                }
                            }
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

    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@UpdatePassword, 0, toolbar1)
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

    //　更新密码
    private suspend fun updatePassword(old: String, now: String, second: String) {
        try {
            val params = mapOf(
                "oldPwd" to old,
                "newPwd" to now,
                "confirmPwd" to second
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@UpdatePassword, "https://apass.sklife.jp/")
            val it = retrofitUils.create(LoginApi::class.java)
                .updatePassword(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
//                DialogUtils.hideLoading(thisDialog)
                val toast = Toast.makeText(applicationContext, "パスワードを変更しました。", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()

                DialogUtils.hideLoading(thisDialog)
                finish()
                overridePendingTransition(
                    R.anim.left_in,
                    R.anim.right_out
                )
            }
            DialogUtils.hideLoading(thisDialog)
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                println("throwable ------------ ${throwable.code()}")
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
        phone.hideSoftInputFromWindow(passwordAgain.windowToken, 0)
    }
}