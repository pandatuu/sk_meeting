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
import java.net.MalformedURLException
import java.net.URL


open class SuccessActivity : AppCompatActivity() {


    private lateinit var toolbar1: Toolbar
    private lateinit var textView1:TextView
    private lateinit var textView2:TextView
    private lateinit var textView3:TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var myRoomNum =
            PreferenceManager.getDefaultSharedPreferences(this)
                .getString("MyRoomNum", "").toString()

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
                    backgroundResource=R.drawable.border_transparent


                    textView1 = textView() {

                        textColor = Color.BLACK
                        setHintTextColor(Color.BLACK)
                        text = myRoomNum
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
                    backgroundResource=R.drawable.border_transparent


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



//
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
//                    backgroundResource=R.drawable.border_transparent
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

                        val num=intent.getStringExtra("MyRoomNum")
                        gotoVideoInterview("xxxx")

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

        initVideoInterview()

    }


    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@SuccessActivity, 0, toolbar1)
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


    //转向视频界面
    private fun gotoVideoInterview(roomNum: String) {

        var userName =
            PreferenceManager.getDefaultSharedPreferences(this)
                .getString("userName", "").toString()

        try {
            //链接视频
            val user=JitsiMeetUserInfo()
            user.displayName=(userName)

            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(roomNum)
                .setUserInfo(user)
                .build()

            val intent = Intent(this, JitsiMeetActivitySon::class.java)
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
    }

    //初始化视频面试
    private fun initVideoInterview() {

        //
        //https://meet.skjob.jp/
        var add = ""
        add =
            PreferenceManager.getDefaultSharedPreferences(this)
                .getString("selected", "https://meet.guanxinqiao.com/").toString()
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


}
