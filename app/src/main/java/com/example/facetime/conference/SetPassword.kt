package com.example.facetime.conference

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class SetPassword : AppCompatActivity() {

    lateinit var passwordFirst: EditText
    lateinit var passwordAgain: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {
            backgroundColor = Color.TRANSPARENT
            onClick {
                closeFocusjianpan()
            }
            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.BOTTOM
                imageView {
                    imageResource = R.mipmap.icon_back
                }.lparams(dip(9), dip(11))
                textView {
                    text = "返回"
                    textSize = 13f
                }.lparams {
                    leftMargin = dip(10)
                }
                onClick {
                    finish()
                    overridePendingTransition(R.anim.left_in, R.anim.right_out)
                }
            }.lparams(matchParent, dip(45)) {
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
                }.lparams(matchParent, dip(45)) {
                    topMargin = dip(15)
                }
                passwordAgain = editText {
                    hint = "请再次输入密码"
                    maxLines = 1
                    padding = dip(5)
                    backgroundColor = Color.WHITE
                }.lparams(matchParent, dip(45)) {
                    topMargin = dip(15)
                }
                button {
                    gravity = Gravity.CENTER
                    text = "下一步"
                    textColor = Color.WHITE
                    backgroundColor = Color.parseColor("#00BFFF")
                    onClick {
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
                                    val intent = Intent(this@SetPassword, SetNickName::class.java)
                                    startActivity(intent)
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

    private fun closeFocusjianpan() {
        //关闭ｅｄｉｔ光标
        passwordFirst.clearFocus()
        passwordAgain.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(passwordFirst!!.windowToken, 0)
        val code = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        code.hideSoftInputFromWindow(passwordAgain!!.windowToken, 0)
    }
}