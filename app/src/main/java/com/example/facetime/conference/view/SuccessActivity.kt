package com.example.facetime.conference.view


import android.Manifest
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

import org.jitsi.meet.sdk.*
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.preference.PreferenceManager
import android.view.KeyEvent
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.example.facetime.R
import com.example.facetime.conference.api.RoomApi
import com.example.facetime.conference.fragment.BackgroundFragment
import com.example.facetime.conference.fragment.ShareFragment
import com.example.facetime.util.RetrofitUtils
import com.facebook.react.bridge.UiThreadUtil
import com.twitter.sdk.android.tweetcomposer.TweetComposer
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.net.URL


open class SuccessActivity : AppCompatActivity(),
    ShareFragment.SharetDialogSelect,
    BackgroundFragment.ClickBack {

    private lateinit var roomApi: RoomApi

    private lateinit var toolbar1: Toolbar
    private lateinit var textView1:TextView

    private var backgroundFragment: BackgroundFragment? = null
    private var shareFragment: ShareFragment? = null
    private lateinit var vertical: FrameLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        var myRoomNum =
//            PreferenceManager.getDefaultSharedPreferences(this)
//                .getString("MyRoomNum", "").toString()

        val mainId = 1
        vertical  = frameLayout {
            id = mainId
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
                    relativeLayout {
//                        imageView {
//                            imageResource = R.mipmap.icon_share_zwxq
//                            setOnClickListener {
//                                addListFragment()
//                            }
//                        }.lparams(wrapContent, wrapContent){
//                            alignParentRight()
//                            centerVertically()
//                            rightMargin = dip(15)
//                        }
                    }.lparams(dip(0),matchParent){
                        weight = 1f
                    }
                }.lparams() {
                    weight = 1f
                    width = dip(0)
                    height = dip(65 - getStatusBarHeight(this@SuccessActivity))
                    topMargin=dip(getStatusBarHeight(this@SuccessActivity))
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }





            verticalLayout {
                gravity = Gravity.CENTER_HORIZONTAL
                textView {
                    text =
                        "恭喜您的专属会议室创建成功啦！"
                    textSize = 20f
                    textColor = Color.BLACK
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams() {
                    height = wrapContent
                    width = matchParent
                }


                textView{
                    text="会议室ID"
                }.lparams() {
                    topMargin = dip(35)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                    height = wrapContent
                    width = matchParent
                }


                linearLayout() {



                    gravity = Gravity.CENTER
                    backgroundResource=R.drawable.border


                    textView1 = textView() {

                        textColor = Color.BLACK
                        setHintTextColor(Color.BLACK)
                        text =  intent.getStringExtra("MyRoomNum")
                        imeOptions = IME_ACTION_DONE
                        backgroundColor=Color.TRANSPARENT
                        singleLine = true
                        gravity=Gravity.CENTER



                    }.lparams() {

                        width = wrapContent
                        height = matchParent

                    }

                }.lparams() {
                    topMargin = dip(5)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                    height = dip(50)
                    width = matchParent
                }





                textView{
                    text="会议室名称"
                }.lparams() {
                    topMargin = dip(35)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                    height = wrapContent
                    width = matchParent
                }

                linearLayout() {



                    gravity = Gravity.CENTER
                    backgroundResource=R.drawable.border


                    textView1 = textView() {

                        textColor = Color.BLACK
                        setHintTextColor(Color.BLACK)
                        text = intent.getStringExtra("RoomName")
                        imeOptions = IME_ACTION_DONE
                        backgroundColor=Color.TRANSPARENT
                        singleLine = true
                        gravity=Gravity.CENTER



                    }.lparams() {

                        width = wrapContent
                        height = matchParent

                    }

                }.lparams() {
                    topMargin = dip(5)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                    height = dip(50)
                    width = matchParent
                }




//                textView{
//                    text="会议室密码"
//                }.lparams() {
//                    topMargin = dip(35)
//                    rightMargin = dip(15)
//                    leftMargin = dip(15)
//                    height = wrapContent
//                    width = matchParent
//                }
//
//                linearLayout() {
//
//
//
//                    gravity = Gravity.CENTER
//                    backgroundResource=R.drawable.border
//
//
//                    textView1 = textView() {
//
//                        textColor = Color.BLACK
//                        setHintTextColor(Color.BLACK)
//                        text = intent.getStringExtra("Password")
//                        imeOptions = IME_ACTION_DONE
//                        backgroundColor=Color.TRANSPARENT
//                        singleLine = true
//                        gravity=Gravity.CENTER
//
//
//                    }.lparams() {
//
//                        width = wrapContent
//                        height = matchParent
//
//                    }
//
//                }.lparams() {
//                    topMargin = dip(5)
//                    rightMargin = dip(15)
//                    leftMargin = dip(15)
//                    height = dip(50)
//                    width = matchParent
//                }
//
//



                textView {
                    text = "进入会议室"
                    textSize = 16f
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    gravity = Gravity.CENTER



                    setOnClickListener {



                        gotoVideoInterview(intent.getStringExtra("MyRoomNum"))

                    }

                }.lparams() {
                    height = dip(50)
                    width = matchParent
                    topMargin = dip(50)
                    rightMargin = dip(15)
                    leftMargin = dip(15)

                }





            }.lparams() {
                topMargin = dip(85)
                rightMargin=dip(15)
                leftMargin=dip(15)
                height = wrapContent
                width = matchParent
            }
        }

        initVideoInterview()

    }


    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@SuccessActivity, 0, toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        toolbar1.setNavigationOnClickListener {
            var intentNow =
                Intent(this@SuccessActivity, MenuActivity::class.java)
            startActivity(intentNow)
            finish()//返回
            overridePendingTransition(
                R.anim.left_in,
                R.anim.right_out
            )
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (event != null) {
            if(keyCode == KeyEvent.KEYCODE_BACK ){
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


    //转向视频界面
    private fun gotoVideoInterview(roomNum:String) {




        val request = JSONObject()

        request.put("id",roomNum)
        request.put("password", "")

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        GlobalScope.launch {

            roomApi = RetrofitUtils(this@SuccessActivity, getString(R.string.roomrUrl))
                .create(RoomApi::class.java)

            val result = roomApi.joinRoom(body)
                .subscribeOn(Schedulers.io())
                .awaitSingle()

            println("消息xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
            println(result)
            println(result.body())

            UiThreadUtil.runOnUiThread(Runnable {
                if (result.code() in 200..299) {





                    val endTime= JSONObject(result.body()?.toString()).get("endTime").toString().toLong()
                    val startTime=
                        JSONObject(result.body()?.toString()).get("startTime").toString().toLong()
                    val time=endTime-startTime

                    val toast = Toast.makeText(
                        applicationContext,
                        "会议将会有时间限制",
                        Toast.LENGTH_SHORT
                    )

                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()

                    try {
                        //链接视频
                        val options = JitsiMeetConferenceOptions.Builder()
                            .setRoom(roomNum)
                            .setAudioMuted(true)
                            .setVideoMuted(true)
                            .setUserInfo(JitsiMeetUserInfo())
                            .build()

                        val intent = Intent(this@SuccessActivity, JitsiMeetActivitySon::class.java)
                        intent.action = "org.jitsi.meet.CONFERENCE"
                        intent.putExtra("JitsiMeetConferenceOptions", options)
                        intent.putExtra("time",time)
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

    override fun clickAll() {
        closeAlertDialog()
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
        } catch (e: Throwable) {
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

    override suspend fun getSelectedItem(index: Int) {
        UMConfigure.init(
            this, "5cdcc324570df3ffc60009c3"
            , "umeng", UMConfigure.DEVICE_TYPE_PHONE, ""
        )
        when (index) {
            0 -> {
                if (Build.VERSION.SDK_INT >= 23) {
                    val mPermissionList = arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_LOGS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.SET_DEBUG_APP,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.WRITE_APN_SETTINGS
                    )
                    ActivityCompat.requestPermissions(this, mPermissionList, 123)
                }
                ShareAction(this)
                    .setPlatform(SHARE_MEDIA.LINE)//传入平台
                    .withText("this is chat App,welcome to try")
                    .setShareboardclickCallback { _, _ -> println("11111111111111111111111111111111111111111 ") }
                    .share()

                //调用创建分享信息接口
            }
            1 -> {
                val builder = TweetComposer.Builder(this)
                builder.text("this is chat App,welcome to try")
                    .show()

                //调用创建分享信息接口
            }
            else -> {
                closeAlertDialog()
            }
        }
    }
    private fun addListFragment() {
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (backgroundFragment == null) {
            backgroundFragment =
                BackgroundFragment.newInstance()
            mTransaction.add(vertical.id, backgroundFragment!!)
        }

        mTransaction.setCustomAnimations(
            R.anim.bottom_in,
            R.anim.bottom_in
        )

        shareFragment = ShareFragment.newInstance()
        mTransaction.add(vertical.id, shareFragment!!)

        mTransaction.commit()
    }
    private fun closeAlertDialog() {

        val mTransaction = supportFragmentManager.beginTransaction()
        if (shareFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.bottom_out, R.anim.bottom_out
            )
            mTransaction.remove(shareFragment!!)
            shareFragment = null
        }

        if (backgroundFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(backgroundFragment!!)
            backgroundFragment = null
        }
        mTransaction.commit()
    }
}
