package com.example.facetime.conference


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

import com.facebook.react.modules.core.PermissionListener
import org.jitsi.meet.sdk.*
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import com.example.facetime.R


open class CreateRoomNameActivity : AppCompatActivity() {


    private lateinit var toolbar1: Toolbar
    private lateinit var editText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            backgroundColor = Color.parseColor("#f2f2f2")

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
                        text="返回"
                        gravity=Gravity.CENTER

                    }.lparams(){
                        height= matchParent
                        width= wrapContent
                    }
                }.lparams() {
                    weight = 1f
                    width = dip(0)
                    height = dip(65 - getStatusBarHeight(this@CreateRoomNameActivity))
                    topMargin=dip(getStatusBarHeight(this@CreateRoomNameActivity))
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }





            verticalLayout {
                gravity = Gravity.CENTER_HORIZONTAL
                textView {
                    text = "为您的会议室取一个别致的名称吧，同事们可以通过查找会议室名称来加入哦~"
                    textSize = 20f
                    textColor = Color.BLACK
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams() {
                    height = wrapContent
                    width = matchParent
                }




                linearLayout() {

                    setOnClickListener {
                        editText.requestFocus()
                        showSoftKeyboard(editText)

                    }

                    gravity = Gravity.CENTER
                    backgroundResource=R.drawable.border


                    editText = editText() {

                        textColor = Color.BLACK
                        setHintTextColor(Color.BLACK)
                        hint = "请输入会议名称"
                        imeOptions = IME_ACTION_DONE
                        backgroundColor=Color.TRANSPARENT
                        singleLine = true

                        addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(
                                s: CharSequence?,
                                start: Int,
                                count: Int,
                                after: Int
                            ) {


                            }

                            override fun onTextChanged(
                                s: CharSequence?,
                                start: Int,
                                before: Int,
                                count: Int
                            ) {
                            }

                            override fun afterTextChanged(s: Editable?) {
                                if (text.toString() == "") {
                                    hint = "请输入会议名称"
                                } else {
                                    hint = ""
                                }
                            }
                        })

                        setOnFocusChangeListener { view, b ->
                            if (b) {
                                setHintTextColor(Color.BLACK)
                            } else {
                                setHintTextColor(Color.GRAY)
                            }
                        }
                    }.lparams() {

                        width = wrapContent
                        height = matchParent

                    }

                }.lparams() {
                    topMargin = dip(40)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                    height = dip(50)
                    width = matchParent
                }



                textView {
                    text = "下一步"
                    textSize = 16f
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    gravity = Gravity.CENTER


                    setOnClickListener {

                        var intent =
                            Intent(this@CreateRoomNameActivity, CreatePasswordActivity::class.java)
                        startActivityForResult(intent, 4)
                        overridePendingTransition(
                            R.anim.right_in,
                            R.anim.left_out
                        )
                    }

                }.lparams() {
                    height = dip(50)
                    width = matchParent
                    topMargin = dip(50)
                    rightMargin = dip(15)
                    leftMargin = dip(15)

                }





            }.lparams() {
                topMargin = dip(20)
                rightMargin=dip(15)
                leftMargin=dip(15)
                height = wrapContent
                width = matchParent
            }
        }
    }


    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@CreateRoomNameActivity, 0, toolbar1)
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


    fun showSoftKeyboard(view: View?) {

        if (view == null || view.windowToken == null) {
            return
        }
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.showSoftInput(view, 0)
    }


}
