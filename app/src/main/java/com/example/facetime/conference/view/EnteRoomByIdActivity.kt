package com.example.facetime.conference.view


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
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.*
import androidx.fragment.app.FragmentTransaction

import com.example.facetime.R
import com.example.facetime.conference.api.RoomApi
import com.example.facetime.conference.fragment.ChooseRoomIdFragment
import com.example.facetime.util.RetrofitUtils
import com.facebook.react.bridge.UiThreadUtil
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo
import org.json.JSONArray
import org.json.JSONObject
import withTrigger
import click
import java.net.MalformedURLException
import java.net.URL


open class EnteRoomByIdActivity : AppCompatActivity() {


    private lateinit var toolbar1: Toolbar

    private lateinit var editText1: EditText
    //private lateinit var editTextName: EditText

    private lateinit var frame: FrameLayout
    private lateinit var triangle: LinearLayout

    private lateinit var roomApi: RoomApi

    private lateinit var switch_video: Switch
    private lateinit var switch_audio: Switch
    lateinit var ms: SharedPreferences
    var flag=1

    private var chooseRoomIdFragment: ChooseRoomIdFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ms = PreferenceManager.getDefaultSharedPreferences(this)

        verticalLayout {

            this.withTrigger().click  {
                closeSoftKeyboard(editText1)
                closeSelector()
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
                    height = dip(65 - getStatusBarHeight(this@EnteRoomByIdActivity))
                    topMargin = dip(getStatusBarHeight(this@EnteRoomByIdActivity))
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }





            verticalLayout {
                gravity = Gravity.CENTER_HORIZONTAL
                textView {
                    text =
                        "请输入要加入的会议室ID和名称"
                    textSize = 20f
                    textColor = Color.BLACK
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams() {
                    height = wrapContent
                    width = matchParent
                }

                var hide = editText {
                    requestFocus()
                }.lparams() {
                    height = dip(0)
                }


                lateinit var textforid: TextView
                lateinit var textforname: TextView

                linearLayout() {
                    backgroundColor = Color.WHITE
                    orientation = LinearLayout.HORIZONTAL
                    relativeLayout() {
                        this.withTrigger().click  {
                            textforid.backgroundResource = R.drawable.border_bottom_transparent
                            textforname.backgroundColor=Color.TRANSPARENT
                            editText1.setText("")
                            editText1.hint="请输入会议室ID"
                            flag=1
                            closeSelector()

                        }

                        textforid = textView {

                            backgroundResource = R.drawable.border_bottom_transparent

                        }.lparams() {
                            height = matchParent
                            width = dip(30)
                            centerInParent()
                            margin = dip(1)

                        }

                        textView {

                            text = "ID"
                            gravity = Gravity.CENTER
                            textColor = Color.BLACK

                        }.lparams() {
                            height = matchParent
                            width = wrapContent
                            centerInParent()
                            margin = dip(1)
                        }

                    }.lparams() {
                        width = dip(0)
                        height = matchParent
                        weight = 1f
                    }
                    relativeLayout() {

                        this.withTrigger().click  {
                            textforname.backgroundResource = R.drawable.border_bottom_transparent
                            textforid.backgroundColor=Color.TRANSPARENT
                            editText1.setText("")
                            editText1.hint="请输入会议室名称"
                            flag=2
                            closeSelector()
                        }

                        textforname = textView {
                            backgroundColor = Color.TRANSPARENT
                            //backgroundResource=R.drawable.border_bottom_transparent

                        }.lparams() {
                            height = matchParent
                            width = dip(30)
                            centerInParent()
                            margin = dip(1)

                        }

                        textView {

                            text = "名称"
                            gravity = Gravity.CENTER
                            textColor = Color.BLACK


                        }.lparams() {
                            height = matchParent
                            width = wrapContent
                            centerInParent()
                            margin = dip(1)

                        }

                    }.lparams() {
                        width = dip(0)
                        height = matchParent
                        weight = 1f
                    }

                }.lparams() {
                    topMargin = dip(30)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                    height = dip(50)
                    width = matchParent
                }









                relativeLayout() {

                    this.withTrigger().click  {
                        editText1.requestFocus()
                        showSoftKeyboard(editText1)

                    }

                    backgroundResource = R.drawable.border


                    linearLayout {
                        gravity = Gravity.CENTER
                        editText1 = editText() {
                            textColor = Color.BLACK
                            setHintTextColor(Color.GRAY)
                            hint = "请输入会议室ID"
                            imeOptions = IME_ACTION_DONE
                            backgroundColor = Color.TRANSPARENT
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
                                        hint = "请输入会议室ID"
                                    } else {
                                        hint = ""
                                    }
                                }
                            })
                            setOnFocusChangeListener { view, b ->
                                if (b) {
                                    setHintTextColor(Color.BLACK)
                                    closeSelector()
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


                    triangle = linearLayout {

                        backgroundColor = Color.WHITE
                        gravity = Gravity.CENTER

                        this.withTrigger().click  {

                            triangle.requestFocus()
                            editText1.clearFocus()

                            if (chooseRoomIdFragment == null) {
                                showSelectort()
                            } else {
                                closeSelector()
                            }


                            closeSoftKeyboard(editText1)

                        }

                        imageView {
                            imageResource = R.mipmap.icon_down_search
                        }.lparams {
                            height = dip(10)
                            width = dip(15)
                            rightMargin = dip(8)
                            leftMargin = dip(8)
                        }
                    }.lparams {
                        height = matchParent
                        width = wrapContent
                        margin = dip(2)
                        alignParentRight()
                        centerInParent()
                    }


                }.lparams() {
                    topMargin = dip(30)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                    height = dip(50)
                    width = matchParent
                }


                val fId = 10
                frame = frameLayout {
                    id = fId




                    verticalLayout {

                        //
//                        relativeLayout() {
//
//                            setOnClickListener {
//                                editTextName.requestFocus()
//                                showSoftKeyboard(editTextName)
//
//                            }
//
//                            backgroundResource = R.drawable.border
//
//
//                            linearLayout {
//                                gravity = Gravity.CENTER
//                                editTextName = editText() {
//                                    textColor = Color.BLACK
//                                    setHintTextColor(Color.GRAY)
//                                    hint = "请输入会议室名"
//                                    imeOptions = IME_ACTION_DONE
//                                    backgroundColor = Color.TRANSPARENT
//                                    singleLine = true
//                                    addTextChangedListener(object : TextWatcher {
//                                        override fun beforeTextChanged(
//                                            s: CharSequence?,
//                                            start: Int,
//                                            count: Int,
//                                            after: Int
//                                        ) {
//                                        }
//
//                                        override fun onTextChanged(
//                                            s: CharSequence?,
//                                            start: Int,
//                                            before: Int,
//                                            count: Int
//                                        ) {
//                                        }
//
//                                        override fun afterTextChanged(s: Editable?) {
//                                            if (text.toString() == "") {
//                                                hint = "请输入会议室名"
//                                            } else {
//                                                hint = ""
//                                            }
//                                        }
//                                    })
//                                    setOnFocusChangeListener { view, b ->
//                                        if (b) {
//                                            setHintTextColor(Color.BLACK)
//                                            closeSelector()
//                                        } else {
//                                            setHintTextColor(Color.GRAY)
//                                        }
//                                    }
//                                }.lparams() {
//                                    width = wrapContent
//                                    height = matchParent
//                                    leftMargin = dip(20)
//                                    rightMargin = dip(20)
//                                }
//
//                            }.lparams() {
//                                centerInParent()
//                                width = matchParent
//                                height = matchParent
//                            }
//
//
//                        }.lparams() {
//                            topMargin = dip(40)
//                            rightMargin = dip(15)
//                            leftMargin = dip(15)
//                            height = dip(50)
//                            width = matchParent
//                        }
//


                        linearLayout() {

                            gravity = Gravity.CENTER_VERTICAL

                            textView {

                                text = "进入会议室时开启语音"
                                gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL

                            }.lparams() {
                                width = dip(0)
                                height = matchParent
                                weight = 1f
                            }


                            switch_audio = switch {

                                setThumbResource(R.drawable.thumb)
                                setTrackResource(R.drawable.track)


                                setChecked(true)
                            }.lparams() {
                                width = dip(50)
                                height = dip(30)
                            }


                        }.lparams() {
                            topMargin = dip(30)
                            height = dip(50)
                            width = matchParent
                            rightMargin = dip(15)
                            leftMargin = dip(15)
                        }



                        linearLayout() {

                            gravity = Gravity.CENTER_VERTICAL

                            textView {

                                text = "进入会议室时开启视频"
                                gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL

                            }.lparams() {
                                width = dip(0)
                                height = matchParent
                                weight = 1f
                            }


                            switch_video = switch {

                                setThumbResource(R.drawable.thumb)
                                setTrackResource(R.drawable.track)

                                setChecked(true)


                            }.lparams() {
                                width = dip(50)
                                height = dip(30)
                            }


                        }.lparams() {
                            topMargin = dip(0)
                            rightMargin = dip(15)
                            leftMargin = dip(15)
                            height = dip(50)
                            width = matchParent
                        }






                        textView {
                            text = "加入"
                            textSize = 16f
                            textColor = Color.WHITE
                            backgroundResource = R.drawable.bottonbg
                            gravity = Gravity.CENTER



                            this.withTrigger().click {

                                if (editText1.text.isNullOrEmpty()&& flag==1) {
                                    val toast = Toast.makeText(
                                        getApplicationContext(),
                                        "请输入房间ID",
                                        Toast.LENGTH_SHORT
                                    )
                                    toast.setGravity(Gravity.CENTER, 0, 0)
                                    toast.show()
                                } else if (editText1.text.isNullOrEmpty()&& flag==2) {
                                    val toast = Toast.makeText(
                                        getApplicationContext(),
                                        "请输入房间名",
                                        Toast.LENGTH_SHORT
                                    )
                                    toast.setGravity(Gravity.CENTER, 0, 0)
                                    toast.show()
                                } else {
                                    findTheRoom()
                                    //gotoVideoInterview(editText1.text.toString())
                                }
                            }

                        }.lparams() {
                            height = dip(50)
                            width = matchParent
                            topMargin = dip(50)

                            rightMargin = dip(15)
                            leftMargin = dip(15)
                        }

                    }.lparams() {
                        height = wrapContent
                        width = matchParent
                    }
                }.lparams() {

                    height = wrapContent
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

        initVideoInterview()
    }


    fun findTheRoom() {


        GlobalScope.launch {

            roomApi = RetrofitUtils(this@EnteRoomByIdActivity, getString(R.string.roomrUrl))
                .create(RoomApi::class.java)

            var id:String?=editText1.text.toString()
            var name:String?=editText1.text.toString()
            if(flag==1){
                name=null
            }else{
                id=null
            }

            val result = roomApi.searchRoom(id, name)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
            println(result)
            println(result.body())

            UiThreadUtil.runOnUiThread(Runnable {

                if (result.code() in 200..299) {

                    val num = JSONObject(result.body()?.toString()).get("id").toString()
                    val name = JSONObject(result.body()?.toString()).get("name").toString()

                    //得到缓存里面的值
                    var usedRoom =
                        PreferenceManager.getDefaultSharedPreferences(this@EnteRoomByIdActivity)
                            .getString("usedRoom", "[]")
                    val array = JSONArray(usedRoom)

                    //判断缓存里面是否已经有值
                    var addFlag = false
                    if (array.length() == 0) {
                        addFlag = true
                    } else {
                        for (i in 0 until array.length()) {
                            if ((array[i] as JSONObject).getString("num") != num) {
                                if (i == array.length() - 1) {
                                    addFlag = true
                                }
                            } else {
                                break
                            }
                        }
                    }

                    //如果没有的话就添加
                    if (addFlag) {

                        if (array.length() >= 5) {
                            array.remove(array.length() - 1)
                        }

                        val ob = JSONObject()
                        ob.put("num", num)
                        ob.put("name", name)

                        array.put(ob)

                        var mEditor: SharedPreferences.Editor = ms.edit()
                        mEditor.putString("usedRoom", array.toString())
                        mEditor.commit()
                    }

                    gotoVideoInterview(num)

//                    var intent =
//                        Intent(this@EnteRoomByIdActivity, EnteRoomByPasswordActivity::class.java)
//
//                    intent.putExtra("roomNum", num)
//                    intent.putExtra("roomName",name )
//                    intent.putExtra("switch_audio",switch_audio.isChecked)
//                    intent.putExtra("switch_video",switch_video.isChecked)
//
//                    startActivity(intent)
//                    overridePendingTransition(
//                        R.anim.right_in,
//                        R.anim.left_out
//                    )

                } else if (result.code() == 406 || result.code() == 404 ) {
                    val toast = Toast.makeText(getApplicationContext(), "房间不存在", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                } else {
                    val toast = Toast.makeText(getApplicationContext(), "出错了!", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    finish();


                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@EnteRoomByIdActivity, 0, toolbar1)
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

    fun showSelectort() {

        //triangle.visibility=View.INVISIBLE

        var mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        chooseRoomIdFragment =
            ChooseRoomIdFragment.newInstance(flag)

        mTransaction.setCustomAnimations(
            R.anim.top_in, R.anim.top_in
        )

        mTransaction.add(frame.id, chooseRoomIdFragment!!)

        mTransaction.commit()
        // toast("xxxxxxxxxxxx")

    }


    fun closeSelector() {
        //triangle.visibility=View.VISIBLE

        var mTransaction = supportFragmentManager.beginTransaction()

//        mTransaction.setCustomAnimations(
//            R.anim.top_out, R.anim.top_out
//        )

        if (chooseRoomIdFragment != null) {
            mTransaction.remove(chooseRoomIdFragment!!)
            chooseRoomIdFragment = null

        }

        mTransaction.commit()


    }


    fun selectRoomToEditText(text: String) {


        editText1.setText(text)
        closeSelector()

    }

    fun selectRoomNameToEditText(text: String) {


        editText1.setText(text)
        closeSelector()

    }


//    //转向视频界面
//    private fun gotoVideoInterview(roomNum: String) {
//
//        val toast = Toast.makeText(
//            applicationContext,
//            "视频会议最多持续半个小时",
//            Toast.LENGTH_SHORT
//        )
//        toast.setGravity(Gravity.CENTER, 0, 0)
//        toast.show()
//
//
//        var usedRoomNum =
//            PreferenceManager.getDefaultSharedPreferences(this)
//                .getStringSet("usedRoomNum", hashSetOf())
//
//        var newRoomSet = setOf<String>()
//
//
//        usedRoomNum?.add(roomNum)
//
//        var mEditor: SharedPreferences.Editor = ms.edit()
//        mEditor.putStringSet("usedRoomNum", usedRoomNum)
//        mEditor.commit()
//
//
//        var userName =
//            PreferenceManager.getDefaultSharedPreferences(this)
//                .getString("userName", "").toString()
//
//        try {
//            var user = JitsiMeetUserInfo()
//            user.setDisplayName(userName)
//
//            //链接视频
//            val options = JitsiMeetConferenceOptions.Builder()
//                .setAudioMuted(!switch_audio.isChecked)
//                .setVideoMuted(!switch_video.isChecked)
//                .setRoom(roomNum)
//                .setUserInfo(user)
//                .build()
//
//            val intent = Intent(this, JitsiMeetActivitySon::class.java)
//            intent.action = "org.jitsi.meet.CONFERENCE"
//            intent.putExtra("JitsiMeetConferenceOptions", options)
//            startActivity(intent)
//
//            overridePendingTransition(
//                R.anim.right_in,
//                R.anim.left_out
//            )
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            System.out.println("错了")
//        }
//    }

    //转向视频界面
    private fun gotoVideoInterview(roomNum:String) {


        val request = JSONObject()

        request.put("id", roomNum)
        request.put("password", "")

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        GlobalScope.launch {

            roomApi = RetrofitUtils(this@EnteRoomByIdActivity, getString(R.string.roomrUrl))
                .create(RoomApi::class.java)

            val result = roomApi.joinRoom(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            println("消息xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
            println(result)
            println(result.body())

            UiThreadUtil.runOnUiThread(Runnable {
                if (result.code() in 200..299) {


                    val endTime =
                        JSONObject(result.body()?.toString()).get("endTime").toString().toLong()
                    val startTime =
                        JSONObject(result.body()?.toString()).get("startTime").toString().toLong()
                    var time = endTime - startTime

                    var level =
                        PreferenceManager.getDefaultSharedPreferences(this@EnteRoomByIdActivity)
                            .getInt("level", 0)
                    if(level!=0){
                        time=0
                    }


                    val toast = Toast.makeText(
                        applicationContext,
                        "会议将会有时间限制!",
                        Toast.LENGTH_SHORT
                    )

                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()

                    try {

                        var user=JitsiMeetUserInfo()

                        var nickName =
                            PreferenceManager.getDefaultSharedPreferences(this@EnteRoomByIdActivity)
                                .getString("nickName", "")

                        user.displayName=nickName

                        println("---------------------"+nickName)

                        //链接视频
                        val options = JitsiMeetConferenceOptions.Builder()
                            .setRoom(roomNum)
                            .setAudioMuted(!switch_audio.isChecked)
                            .setVideoMuted(!switch_video.isChecked)
                            .setUserInfo(user)
                            .build()

                        val intent =
                            Intent(this@EnteRoomByIdActivity, JitsiMeetActivitySon::class.java)
                        intent.action = "org.jitsi.meet.CONFERENCE"
                        intent.putExtra("JitsiMeetConferenceOptions", options)
                        intent.putExtra("time", time)
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

    //初始化视频面试
    private fun initVideoInterview() {

        //
        //https://meet.skjob.jp/
        var add = ""
        add =
            PreferenceManager.getDefaultSharedPreferences(this)
                .getString("serviceAdd", getString(R.string.videoUrl)).toString()
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

}
