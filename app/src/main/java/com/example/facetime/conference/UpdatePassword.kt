package com.example.facetime.conference

import android.content.Context
import android.graphics.Color
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

class UpdatePassword: AppCompatActivity() {

    private lateinit var passwordFirst: EditText
    private lateinit var passwordAgain: EditText
    private lateinit var toolbar1: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {
            backgroundColor = Color.TRANSPARENT
            setOnClickListener {
                closeFocusjianpan()
            }
            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.BOTTOM
                toolbar1 = toolbar {
                    navigationIconResource = R.mipmap.icon_back
                }.lparams(dip(9), dip(15))
                textView {
                    text = "返回"
                    textSize = 13f
                }.lparams {
                    leftMargin = dip(10)
                }
                setOnClickListener {
                    finish()
                    overridePendingTransition(R.anim.left_in, R.anim.right_out)
                }
            }.lparams(matchParent, dip(55)) {
                setMargins(dip(15), 0, dip(15), 0)
            }
            linearLayout {
                orientation = LinearLayout.VERTICAL
                textView {
                    text = "设置密码"
                    textSize = 23f
                }.lparams(wrapContent, wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                passwordFirst = editText {
                    hint = "请输入密码"
                    maxLines = 1
                    padding = dip(5)
                    backgroundColor = Color.WHITE
                    setOnKeyListener(object : View.OnKeyListener{
                        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                            if (event != null) {
                                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.action) {
                                    //处理事件
                                    passwordAgain.requestFocus()
                                    return true
                                }
                            }
                            return false
                        }
                    })
                }.lparams(matchParent, dip(45)) {
                    topMargin = dip(15)
                }
                passwordAgain = editText {
                    hint = "请再次输入密码"
                    maxLines = 1
                    padding = dip(5)
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
                }.lparams(matchParent, dip(45)) {
                    topMargin = dip(15)
                }
                button {
                    gravity = Gravity.CENTER
                    text = "下一步"
                    textColor = Color.WHITE
                    backgroundColor = Color.parseColor("#00BFFF")
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
                                    finish()
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