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

class UpdateNickName: AppCompatActivity() {

    lateinit var nickName: EditText
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
                }.lparams(wrapContent, wrapContent) {
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
                    text = "设置新昵称"
                    textSize = 23f
                }.lparams {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                nickName = editText {
                    hint = "请输入新昵称"
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

    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@UpdateNickName, 0, toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }

    private fun closeFocusjianpan() {
        //关闭ｅｄｉｔ光标
        nickName.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(nickName.windowToken, 0)
    }
}