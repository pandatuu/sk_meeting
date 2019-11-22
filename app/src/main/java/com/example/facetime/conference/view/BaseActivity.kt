package com.example.facetime.conference.view


import android.Manifest
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import java.net.MalformedURLException
import java.net.URL

import org.jitsi.meet.sdk.*
import android.content.Intent
import android.content.SharedPreferences
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import android.os.Build
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo.IME_ACTION_GO
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import click
import com.example.facetime.R
import com.example.facetime.setting.view.SettingsActivity
import com.example.facetime.conference.listener.VideoChatControllerListener
import com.example.facetime.conference.fragment.BackgroundFragment
import com.example.facetime.conference.fragment.ShareFragment
import com.twitter.sdk.android.tweetcomposer.TweetComposer
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA
import withTrigger


open class BaseActivity : AppCompatActivity(),
    ShareFragment.SharetDialogSelect, BackgroundFragment.ClickBack {
    override fun clickAll() {
        closeAlertDialog()
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



    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(requestCode, resultCode, data)
//        JitsiMeetActivityDelegate.onActivityResult(
//            this, requestCode, resultCode, data
//        )


        var b = data?.hasExtra("ip")

        var selected = data?.getStringExtra("ip")


        if (selected != null) {


            initVideoInterview(selected.toString())
        }


    }
    lateinit var ms: SharedPreferences

    private var backgroundFragment: BackgroundFragment? = null
    private var shareFragment: ShareFragment? = null
    private lateinit var vertical: FrameLayout
    var videoChatControllerListener: VideoChatControllerListener =
        JitsiMeetActivitySon()
    private lateinit var toolbar1: Toolbar
    var cont = this
    lateinit var editText: EditText
    var defaultValue =getString(R.string.videoUrl)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ms = PreferenceManager.getDefaultSharedPreferences(this)


        val mainId = 1
        vertical = frameLayout {
            id = mainId

            this.withTrigger().click  {


            closeSoftKeyboard(editText)
                editText.clearFocus()
            }

            backgroundResource = R.mipmap.company_bg

            relativeLayout() {


                textView() {
                }.lparams() {
                    width = matchParent
                    height = dip(0)

                }

                relativeLayout() {

                    backgroundResource = R.color.transparent

                    toolbar1 = toolbar {
                        backgroundResource = R.color.transparent
                        isEnabled = true
                        title = ""


                    }.lparams() {
                        width = matchParent
                        height = dip(0)
                        alignParentBottom()

                    }

                    textView {
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColorResource = R.color.toolBarTextColor
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))

                    }.lparams() {
                        width = matchParent
                        height = wrapContent
                        height = dip(0)
                        alignParentBottom()
                    }

                    textView {
                        textColorResource = R.color.saveButtonTextColor
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER_VERTICAL
                        textSize = 13f

                    }.lparams() {
                        width = dip(52)
                        height = dip(0)
                        alignParentRight()
                        alignParentBottom()
                    }
                }.lparams() {
                    width = matchParent
                    height = dip(0)
                }
            }.lparams() {
                width = matchParent
                height = dip(0)
            }







            relativeLayout() {


                linearLayout() {

                    gravity = Gravity.RIGHT or Gravity.BOTTOM


                    imageView() {
                        imageResource = R.mipmap.icon_share_zwxq
                        scaleType = ImageView.ScaleType.CENTER

                        this.withTrigger().click  {


                        addListFragment()
                        }

                    }.lparams() {
                        rightMargin = dip(10)
                        bottomMargin = dip(0)
                    }




                    imageView() {
                        imageResource = R.mipmap.settings
                        scaleType = ImageView.ScaleType.CENTER

                        this.withTrigger().click  {


                        var intent = Intent(cont, SettingsActivity::class.java)
                            startActivityForResult(intent, 2)
                            overridePendingTransition(
                                R.anim.right_in,
                                R.anim.left_out
                            )

                        }

                    }.lparams() {
                        rightMargin = dip(0)
                        bottomMargin = dip(0)
                    }


                }.lparams() {
                    alignParentTop()
                    rightMargin = dip(10)
                    leftMargin = dip(10)
                    height = dip(50)
                    width = matchParent
                }


                linearLayout() {

                    this.withTrigger().click  {

                    editText.requestFocus()
                        showSoftKeyboard(editText)

                    }

                    gravity = Gravity.CENTER
                    backgroundResource = R.drawable.edittext_bg


                    editText = editText() {
                        textColor = Color.WHITE
                        hint = "请输入房间号"
                        setHintTextColor(Color.GRAY)
                        backgroundResource = R.drawable.edittext_bg
                        singleLine = true
                        imeOptions = IME_ACTION_GO

                        setOnEditorActionListener { v, actionId, event ->


                            goToFaceTime()
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
                                    hint = "请输入房间号"
                                } else {
                                    hint = ""
                                }
                            }

                        })

                        setOnFocusChangeListener { view, b ->

                            if (b) {

                                setHintTextColor(Color.WHITE)

                            } else {
                                setHintTextColor(Color.GRAY)

                            }

                        }

                    }.lparams() {

                        width = wrapContent
                        height = matchParent

                    }


                }.lparams() {
                    alignParentTop()
                    topMargin = dip(60)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                    height = dip(50)
                    width = matchParent
                }



                textView {
                    text = "进入房间"
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    gravity = Gravity.CENTER
                    textSize = 20F

                    this.withTrigger().click  {


                    goToFaceTime()

                    }
                }.lparams() {
                    alignParentBottom()
                    width = matchParent
                    height = dip(50)
                    bottomMargin = dip(10)
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                }

            }.lparams() {
                height = matchParent
                width = matchParent
            }

        }



        initVideoInterview(null)

    }

    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@BaseActivity, 0, toolbar1)
//        getWindow().getDecorView()
//            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//


    }


    fun setVideoChatControllerListener(videoChatControllerListener1: JitsiMeetActivitySon) {
        this.videoChatControllerListener = videoChatControllerListener1
    }


    fun closeSoftKeyboard(view: View?) {
        if (view == null || view.windowToken == null) {
            return
        }
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showSoftKeyboard(view: View?) {
        if (view == null || view.windowToken == null) {
            return
        }
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.showSoftInput(view, 0)
    }


    fun goToFaceTime() {


        if (editText.text == null || editText.text.toString().trim() == "") {

            val toast = Toast.makeText(applicationContext, "请出入房间号", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return
        }


        val num = editText.text
        gotoVideoInterview(num.toString())

    }

    //初始化视频面试
    private fun initVideoInterview(s: String?) {

//        val serverURL: URL
//        try {
//            serverURL = URL("https://meet.skjob.jp/")
//        } catch (e: MalformedURLException) {
//            e.printStackTrace()
//            throw RuntimeException("Invalid server URL!")
//        }
//
//        val defaultOptions = JitsiMeetConferenceOptions.Builder()
//            .setServerURL(serverURL)
//            .setWelcomePageEnabled(false)
//            .build()
//        JitsiMeet.setDefaultConferenceOptions(defaultOptions)
//


        var add = ""
        if (s == null) {
            add =
                PreferenceManager.getDefaultSharedPreferences(this)
                    .getString("selected", defaultValue).toString()
        } else {
            add = s
        }

      lateinit  var serverURL: URL
        try {
            toast(add)

            serverURL = URL(add)
        } catch (e: MalformedURLException) {
            e.printStackTrace()

            var selected =
                PreferenceManager.getDefaultSharedPreferences(this@BaseActivity)
                    .getString("selected", defaultValue).toString()


            var selectedSet =
                PreferenceManager.getDefaultSharedPreferences(this@BaseActivity)
                    .getStringSet("selectedSet",mutableSetOf(defaultValue) )




                var mEditor: SharedPreferences.Editor = ms.edit()
                mEditor.putString("selected", defaultValue)


                selectedSet?.remove(selected)
                mEditor.putStringSet("selectedSet", selectedSet)


                mEditor.commit()

                serverURL = URL(defaultValue)

            toast("无效地址！")



        }


            //throw RuntimeException("Invalid server URL!")


        try {
            val defaultOptions = JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                .setAudioMuted(false)
                .setVideoMuted(false)
                .setAudioOnly(false)
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeet.setDefaultConferenceOptions(defaultOptions)

        }catch (e: Exception) {
            System.out.println("错了")
        }

    }


    //转向视频界面
    private fun gotoVideoInterview(roomNum: String) {

//        val options = JitsiMeetConferenceOptions.Builder()
//            .setRoom(roomNum)
//            .build()
//        // Launch the new activity with the given options. The launch() method takes care
//        // of creating the required Intent and passing the options.
//        JitsiMeetActivity.launch(this, options)

        try {

        //链接视频
        val options = JitsiMeetConferenceOptions.Builder()
            .setRoom(roomNum)
            .setUserInfo(JitsiMeetUserInfo())
            .build()

//
            JitsiMeetActivitySon().launch(this, options, "xxx")
        }catch (e: Exception) {
            System.out.println("错了")

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
