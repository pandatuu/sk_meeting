package com.example.facetime.login

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class RsetPasswordActivity : AppCompatActivity(){
    lateinit var password:EditText
    lateinit var confirmPassword:EditText
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
                        height = dip(65 - getStatusBarHeight(this@RsetPasswordActivity))
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
                        height = dip(65 - getStatusBarHeight(this@RsetPasswordActivity))
                        topMargin = dip(getStatusBarHeight(this@RsetPasswordActivity))
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(65)
                }
                textView {
                    text = "重置密码"
                    gravity = Gravity.CENTER
                    textSize = 21f
                    typeface = Typeface.DEFAULT_BOLD
                    textColor = Color.BLACK
                }.lparams(height = wrapContent,width = matchParent){
                    topMargin = dip(75)
                }

                linearLayout {
                    backgroundResource = R.drawable.border
                    password = editText {
                        hint = "请输入新密码"
                        setHintTextColor(Color.GRAY)
                        textColor = Color.BLACK
                        backgroundColor = Color.TRANSPARENT
                        setOnKeyListener(object : View.OnKeyListener{
                            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                                if (event != null) {
                                    if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.action) {
                                        //处理事件
                                        confirmPassword.requestFocus()
                                        return true
                                    }
                                }
                                return false
                            }
                        })
                    }.lparams(width = matchParent,height = wrapContent){
                        weight = 1f
                        leftMargin=dip(10)
                    }
                }.lparams(height = wrapContent,width = matchParent){
                    topMargin = dip(25)
                }

                linearLayout {
                    backgroundResource = R.drawable.border
                    textView {

                    }.lparams(width = wrapContent,height = matchParent){
                        topMargin = dip(10)
                    }

                    confirmPassword = editText {
                        hint = "请再次输入新密码"
                        setHintTextColor(Color.GRAY)
                        textColor = Color.BLACK
                        backgroundColor = Color.TRANSPARENT
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
                        leftMargin=dip(10)
                    }
                }.lparams(height = wrapContent,width = matchParent){
                    topMargin = dip(10)
                    leftPadding = dip(10)
                    rightPadding = dip(10)
                }


                button {
                    backgroundResource = R.drawable.bottonbg
                    text = "完成"
                    textColor = Color.WHITE
                    textSize = 21f

                    onClick {
                        fulfill()
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
        StatusBarUtil.setTranslucentForImageView(this@RsetPasswordActivity, 0, toolbar1)
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
    private fun closeFocusjianpan() {
        //关闭ｅｄｉｔ光标
        password.clearFocus()
        confirmPassword.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(password.windowToken, 0)
        val code = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        code.hideSoftInputFromWindow(confirmPassword.windowToken, 0)
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
    private fun fulfill(){
        val myPassword = password.text.toString().trim()
        val myConfirmPassword = confirmPassword.text.toString().trim()

        if(myPassword.isNullOrEmpty()){
            toast("密码不可为空！！")
            return
        }

        if(myConfirmPassword.isNullOrEmpty()){
            toast("确认密码不可为空")
            return
        }

        if(myPassword != myConfirmPassword){
            toast("密码前后不一致")
            return
        }

        startActivity<StartActivity>()
        this@RsetPasswordActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }
}