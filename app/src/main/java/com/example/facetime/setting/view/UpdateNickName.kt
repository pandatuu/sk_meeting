package com.example.facetime.setting.view

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
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
import com.alibaba.fastjson.JSON
import com.example.facetime.R
import com.example.facetime.login.api.LoginApi
import com.example.facetime.setting.api.SettingApi
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

class UpdateNickName : AppCompatActivity() {

    lateinit var nickName: EditText
    private lateinit var toolbar1: Toolbar
    lateinit var saveTool: SharedPreferences
    var thisDialog: MyDialog? = null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!) {
            val toast = Toast.makeText(
                this@UpdateNickName,
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

        saveTool = PreferenceManager.getDefaultSharedPreferences(this@UpdateNickName)

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
                    height = dip(65 - getStatusBarHeight(this@UpdateNickName))
                    topMargin = dip(getStatusBarHeight(this@UpdateNickName))
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }
            linearLayout {
                orientation = LinearLayout.VERTICAL
                textView {
                    text = "设置新昵称"
                    textSize = 21f
                    typeface = Typeface.DEFAULT_BOLD
                    textColor = Color.BLACK
                }.lparams {
                    gravity = Gravity.CENTER_HORIZONTAL
                }
                relativeLayout {
                    backgroundResource = R.drawable.border
                    nickName = editText {
                        hint = "请输入新昵称"
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
                    setOnClickListener {
                        closeFocusjianpan()
                        if (nickName.text.toString() != "") {
                            if (nickName.text.length < 10) {
                                thisDialog = DialogUtils.showLoading(this@UpdateNickName)
                                mHandler.postDelayed(r, 12000)
                                GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
                                    updateNickName(nickName.text.toString())
                                }
                            } else {
                                toast("限制字数长度10以内")
                            }
                        } else {
                            toast("请输入名字")
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
        StatusBarUtil.setTranslucentForImageView(this@UpdateNickName, 0, toolbar1)
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

    //　更新昵称
    private suspend fun updateNickName(nickName: String) {
        try {
            val params = mapOf(
                "nickName" to nickName
            )
            val userJson = JSON.toJSONString(params)
            val body = RequestBody.create(MimeType.APPLICATION_JSON, userJson)

            val retrofitUils = RetrofitUtils(this@UpdateNickName, getString(R.string.roomrUrl))
            val it = retrofitUils.create(SettingApi::class.java)
                .updateNickName(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            if (it.code() in 200..299) {
//                DialogUtils.hideLoading(thisDialog)
//                val toast = Toast.makeText(applicationContext, "パスワードを変更しました。", Toast.LENGTH_SHORT)
//                toast.setGravity(Gravity.CENTER,0,0)
//                toast.show()

                val mEditor: SharedPreferences.Editor = saveTool.edit()
                mEditor.putString("userName", nickName)
                mEditor.commit()

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
        nickName.clearFocus()
        //关闭键盘事件
        val phone = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        phone.hideSoftInputFromWindow(nickName.windowToken, 0)
    }
}