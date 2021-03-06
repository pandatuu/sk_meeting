package com.example.facetime.setting.view

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import click
import com.example.facetime.R
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import withTrigger

class SetServiceAddr : AppCompatActivity() {

    lateinit var address: EditText
    private lateinit var toolbar1: Toolbar
    lateinit var saveTool: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        saveTool = PreferenceManager.getDefaultSharedPreferences(this@SetServiceAddr)

        frameLayout {
            backgroundColor = Color.TRANSPARENT
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
                    height = dip(65 - getStatusBarHeight(this@SetServiceAddr))
                    topMargin = dip(getStatusBarHeight(this@SetServiceAddr))
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }
            linearLayout {
                orientation = LinearLayout.VERTICAL
                textView {
                    text = "设置服务器地址"
                    textSize = 21f
                    typeface = Typeface.DEFAULT_BOLD
                    textColor = Color.BLACK
                }.lparams {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                relativeLayout {
                    backgroundResource = R.drawable.border
                    address = editText {
                        hint = getString(R.string.videoUrl)
                        singleLine = true
                        padding = dip(5)
                        setHintTextColor(Color.GRAY)
                        textColor = Color.BLACK
                        backgroundColor = Color.TRANSPARENT
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
                    this.withTrigger().click  {

                        closeFocusjianpan()
                        if (address.text.toString() != "") {

                            val toast = Toast.makeText(
                                applicationContext,
                                "设置成功",
                                Toast.LENGTH_SHORT
                            )
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()

                            val mEditor: SharedPreferences.Editor = saveTool.edit()
                            mEditor.putString("serviceAdd", address.text.toString())
                            mEditor.commit()
                            finish()
                            overridePendingTransition(
                                R.anim.left_in,
                                R.anim.right_out
                            )
                        } else {
                            toast("请输入地址")
                        }
                    }
                }.lparams(matchParent, dip(50)) {
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
        StatusBarUtil.setTranslucentForImageView(this@SetServiceAddr, 0, toolbar1)
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
        address.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(address.windowToken, 0)
    }
}