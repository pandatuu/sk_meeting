package com.example.facetime.conference

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import org.jetbrains.anko.*

class RegisterSetNickName : AppCompatActivity() {

    private lateinit var nickName: EditText

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
                imageView {
                    imageResource = R.mipmap.icon_back
                }.lparams(dip(9), dip(11))
                textView {
                    text = "返回"
                }.lparams(wrapContent, wrapContent) {
                    leftMargin = dip(10)
                }
                setOnClickListener {
                    finish()
                    overridePendingTransition(R.anim.left_in, R.anim.right_out)
                }
            }.lparams(matchParent, dip(45)) {
                setMargins(dip(15), 0, dip(15), 0)
            }
            linearLayout {
                orientation = LinearLayout.VERTICAL
                textView {
                    text = "设置昵称"
                    textSize = 23f
                }.lparams {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                nickName = editText {
                    hint = "请输入昵称"
                    maxLines = 1
                    padding = dip(5)
                    backgroundColor = Color.WHITE
                }.lparams(matchParent,dip(45)){
                    topMargin = dip(15)
                }
                button {
                    gravity = Gravity.CENTER
                    text = "完成注册"
                    textColor = Color.WHITE
                    backgroundColor = Color.parseColor("#00BFFF")
                    setOnClickListener {
                        closeFocusjianpan()
                        if(nickName.text.toString() != ""){
                            if(nickName.text.length < 10){
                                startActivity<MenuActivity>()
                                finish()
                            }else{
                                toast("限制字数长度10以内")
                            }
                        }else{
                            toast("请输入名字")
                        }
                    }
                }.lparams(matchParent,dip(50)){
                    topMargin = dip(30)
                }
            }.lparams(matchParent, wrapContent) {
                setMargins(dip(15), dip(150), dip(15), 0)
            }
        }
    }
    private fun closeFocusjianpan() {
        //关闭ｅｄｉｔ光标
        nickName.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(nickName.windowToken, 0)
    }
}