package com.example.facetime.conference.view


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import android.graphics.Typeface
import android.preference.PreferenceManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.*
import com.example.facetime.R
import com.example.facetime.conference.api.RoomApi
import com.example.facetime.util.RetrofitUtils
import com.facebook.react.bridge.UiThreadUtil
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL


open class EnteRoomByPasswordActivity : AppCompatActivity() {
    private lateinit var toolbar1: Toolbar
    private lateinit var editText1: EditText

    private lateinit var roomApi: RoomApi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            setOnClickListener {
                closeSoftKeyboard(editText1)
            }
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
                        text = "返回"
                        gravity = Gravity.CENTER

                    }.lparams() {
                        height = matchParent
                        width = wrapContent
                    }
                }.lparams() {
                    weight = 1f
                    width = dip(0)
                    height = dip(65 - getStatusBarHeight(this@EnteRoomByPasswordActivity))
                    topMargin = dip(getStatusBarHeight(this@EnteRoomByPasswordActivity))
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }
            verticalLayout {
                gravity = Gravity.CENTER_HORIZONTAL
                textView {
                    text =
                        "该会议室需要访问密码"
                    textSize = 20f
                    textColor = Color.BLACK
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams() {
                    height = wrapContent
                    width = matchParent
                }
                textView {
                    gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
                    text = "会议室ID  "+intent.getStringExtra("roomNum")
                    textColorResource = R.color.black20
                }.lparams() {
                    height = dip(30)
                    width = matchParent
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                    topMargin = dip(50)
                }
                textView {
                    gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
                    text = "会议室名称  "+intent.getStringExtra("roomName")
                    textColorResource = R.color.black20
                }.lparams() {
                    height = dip(30)
                    width = matchParent
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                }
                textView {
                    gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
                    text = "请输入会议室密码"
                    textColorResource = R.color.black20
                }.lparams() {
                    height = dip(30)
                    width = matchParent
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                }
                relativeLayout() {
                    setOnClickListener {
                        editText1.requestFocus()
                        showSoftKeyboard(editText1)
                    }
                    backgroundResource = R.drawable.border
                    linearLayout {
                        gravity = Gravity.CENTER
                        editText1 = editText() {
                            textColor = Color.BLACK
                            setHintTextColor(Color.GRAY)
                            hint = "请输入房间密码"
                            imeOptions = IME_ACTION_DONE
                            backgroundColor = Color.TRANSPARENT
                            singleLine = true
                            inputType =
                                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                            setOnEditorActionListener { v, actionId, event ->
                                println("xxxxxxxxxxxxxxxxxx")
                                println(actionId)
                                println(IME_ACTION_DONE)
                                if (IME_ACTION_DONE == actionId) {
                                    gotoVideoInterview()

                                }
                                false
                            }
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
                                        hint = "请输入房间密码"
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
                            leftMargin = dip(20)
                            rightMargin = dip(20)
                        }
                    }.lparams() {
                        centerInParent()
                        width = matchParent
                        height = matchParent
                    }
                }.lparams() {
                    topMargin = dip(20)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                    height = dip(50)
                    width = matchParent
                }
            }.lparams() {
                topMargin = dip(20)
                rightMargin = dip(15)
                leftMargin = dip(15)
                height = wrapContent
                width = matchParent
            }
        }
    }
    override fun onStart() {
        super.onStart()
        initVideoInterview()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@EnteRoomByPasswordActivity, 0, toolbar1)
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


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (event != null) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
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
    fun closeSoftKeyboard(view: View?) {
        (view as EditText).clearFocus()
        if (view == null || view.windowToken == null) {
            return
        }
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    //初始化视频面试
    private fun initVideoInterview() {
        var add = ""
        add =
            PreferenceManager.getDefaultSharedPreferences(this)
                .getString("selected", getString(R.string.videoUrl)).toString()
        lateinit var serverURL: URL
        try {
            serverURL = URL(add)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        try {
            val defaultOptions = JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                .setAudioMuted(false)
                .setVideoMuted(false)
                .setAudioOnly(false)
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeet.setDefaultConferenceOptions(defaultOptions)
        } catch (e: Exception) {
            System.out.println("错了")
        }
    }
    //转向视频界面
    private fun gotoVideoInterview() {




        val roomNum=intent.getStringExtra("roomNum")
        val request = JSONObject()

        request.put("id", intent.getStringExtra("roomNum"))
        request.put("password", editText1.text.toString())

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        GlobalScope.launch {

            roomApi = RetrofitUtils(this@EnteRoomByPasswordActivity, getString(R.string.roomrUrl))
                .create(RoomApi::class.java)

            val result = roomApi.joinRoom(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            println("消息xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
            println(result)
            println(result.body())

            UiThreadUtil.runOnUiThread(Runnable {
                if (result.code() in 200..299) {

                    val toast = Toast.makeText(
                        applicationContext,
                        "视频会议最多持续半个小时",
                        Toast.LENGTH_SHORT
                    )

                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()

                    try {
                        //链接视频
                        val options = JitsiMeetConferenceOptions.Builder()
                            .setRoom(roomNum)
                            .setAudioMuted(!intent.getBooleanExtra("switch_audio",false))
                            .setVideoMuted(!intent.getBooleanExtra("switch_video",false))
                            .setUserInfo(JitsiMeetUserInfo())
                            .build()

                        val intent = Intent(this@EnteRoomByPasswordActivity, JitsiMeetActivitySon::class.java)
                        intent.action = "org.jitsi.meet.CONFERENCE"
                        intent.putExtra("JitsiMeetConferenceOptions", options)
                        startActivity(intent)

                        overridePendingTransition(
                            R.anim.right_in,
                            R.anim.left_out
                        )

                    } catch (e: Exception) {
                        e.printStackTrace()
                        System.out.println("错了")
                    }

                } else {
                    val toast = Toast.makeText(getApplicationContext(), "密码错误!", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }


            })
        }



    }
}
