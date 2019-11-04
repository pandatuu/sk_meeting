package com.example.facetime.conference

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import android.content.Intent
import android.preference.PreferenceManager
import android.view.View
import android.widget.*
import com.example.facetime.R

open class MenuActivity : AppCompatActivity() {


    private lateinit var toolbar1: Toolbar


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        var b = data?.hasExtra("ip")
        var selected = data?.getStringExtra("ip")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val mainId = 1
        verticalLayout {
            backgroundColor = Color.parseColor("#f2f2f2")
            //toolbar
            relativeLayout() {
                backgroundColor=Color.TRANSPARENT
                toolbar1 = toolbar {
                    backgroundColor=Color.TRANSPARENT
                    isEnabled = true
                    title = ""
                }.lparams() {
                    width = matchParent
                    height = dip(0)
                    alignParentBottom()

                }
            }.lparams() {
                width = matchParent
                height = dip(0)
            }



            linearLayout() {
                gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL

                imageView() {
                    imageResource = R.mipmap.settings
                    setOnClickListener {

                        var intent = Intent(this@MenuActivity, SystemSettingsActivity::class.java)
                        startActivityForResult(intent, 2)
                        overridePendingTransition(
                            R.anim.right_in,
                            R.anim.left_out
                        )

                    }

                }.lparams() {
                    height = dip(20)
                    width = dip(20)
                    topMargin = dip(5)
                    rightMargin = dip(20)
                }

            }.lparams() {
                width = matchParent
                height = dip(100)
            }

            linearLayout() {
                gravity = Gravity.CENTER
                textView {
                    text = "欢迎使用SK-Meeting在线视频会议系统"
                    textSize = 20f
                    textColor = Color.BLACK
                    gravity = Gravity.CENTER
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams() {
                    height = dip(40)
                    width = wrapContent
                }

            }.lparams() {
                width = matchParent
                height = wrapContent
                topMargin = dip(20)
            }


            verticalLayout {
                gravity = Gravity.CENTER_HORIZONTAL



                var RoomNum =
                    PreferenceManager.getDefaultSharedPreferences(this@MenuActivity)
                        .getString("MyRoomName", "").toString()



                println("-------------")
                println(RoomNum.toString())
                println("-------------")
                if(RoomNum==""){
                    textView {
                        text = "创建我的会议室"
                        textSize = 14f
                        textColor = Color.WHITE
                        backgroundResource = R.drawable.bottonbg
                        gravity = Gravity.CENTER

                        setOnClickListener {

                            var intent = Intent(this@MenuActivity, CreateRoomNameActivity::class.java)
                            startActivityForResult(intent, 3)
                            overridePendingTransition(
                                R.anim.right_in,
                                R.anim.left_out
                            )

                        }


                    }.lparams() {
                        height = dip(40)
                        width = dip(240)
                        topMargin = dip(20)
                    }
                }else{
                    textView {
                        text = "进入我的会议室"
                        textSize = 14f
                        textColor = Color.WHITE
                        backgroundResource = R.drawable.bottonbg
                        gravity = Gravity.CENTER



                        setOnClickListener {

                            var intentNow =
                                Intent(this@MenuActivity, SuccessActivity::class.java)



                            var MyRoomName =
                                PreferenceManager.getDefaultSharedPreferences(this@MenuActivity)
                                    .getString("MyRoomName", "").toString()


                            intentNow.putExtra("RoomName",MyRoomName)
                            startActivityForResult(intentNow, 50)
                            overridePendingTransition(
                                R.anim.right_in,
                                R.anim.left_out
                            )
                        }



                    }.lparams() {
                        height = dip(40)
                        width = dip(240)
                        topMargin = dip(20)
                    }
                }




                textView {
                    text = "加入一个会议室"
                    textSize = 14f
                    textColor = Color.WHITE
                    backgroundResource = R.drawable.bottonbg
                    gravity = Gravity.CENTER



                    setOnClickListener {

                        var intent = Intent(this@MenuActivity, EnteRoomByIdActivity::class.java)
                        startActivityForResult(intent, 12)
                        overridePendingTransition(
                            R.anim.right_in,
                            R.anim.left_out
                        )

                    }


                }.lparams() {
                    height = dip(40)
                    width = dip(240)
                    topMargin = dip(20)

                }

            }.lparams() {
                width = matchParent
                height = wrapContent
                topMargin = dip(40)
            }


        }

    }

    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MenuActivity, 0, toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)


    }


}
