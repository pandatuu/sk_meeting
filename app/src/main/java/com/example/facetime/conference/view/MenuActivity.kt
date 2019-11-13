package com.example.facetime.conference.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.preference.PreferenceManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import com.example.facetime.R
import androidx.fragment.app.FragmentTransaction
import com.example.facetime.setting.view.SystemSettingsActivity
import com.example.facetime.setting.view.UpdateTipsFrag
import com.example.facetime.conference.fragment.BackgroundFragment
import com.example.facetime.login.view.StartActivity


open class MenuActivity : AppCompatActivity(),
    UpdateTipsFrag.ButtomCLick,
    BackgroundFragment.ClickBack{

    private lateinit var toolbar1: Toolbar
    private var shadowFragment: BackgroundFragment? = null
    private var updateTips: UpdateTipsFrag? = null

    private var fram: FrameLayout? = null
    private lateinit var createMy:TextView
    private lateinit var enterMy:TextView
    private var exitTime: Long = 0

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
        fram = frameLayout {
            id = mainId
            backgroundColor = Color.parseColor("#f2f2f2")
            linearLayout {
                orientation = LinearLayout.VERTICAL
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

                    createMy=  textView {
                        text = "创建我的会议室"
                        textSize = 14f
                        textColor = Color.WHITE
                        backgroundResource = R.drawable.bottonbg
                        gravity = Gravity.CENTER
                        visibility=View.GONE
                        setOnClickListener {




                            val sp = PreferenceManager.getDefaultSharedPreferences(context)
                            val token = sp.getString("token", "")
                            println("本机token为：$token")

                            if (token.isNullOrEmpty()) {
                                startActivity<StartActivity>()
                                overridePendingTransition(
                                    R.anim.right_in,
                                    R.anim.left_out
                                )
                            } else {
                                startActivity<CreateRoomNameActivity>()
                                overridePendingTransition(
                                    R.anim.right_in,
                                    R.anim.left_out
                                )
                            }


                        }


                    }.lparams() {
                        height = dip(40)
                        width = dip(240)
                        topMargin = dip(20)
                    }


                    enterMy=  textView {
                        text = "进入我的会议室"
                        textSize = 14f
                        textColor = Color.WHITE
                        backgroundResource = R.drawable.bottonbg
                        gravity = Gravity.CENTER
                        visibility=View.GONE


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
        //检查更新
        val version = getLocalVersion(this@MenuActivity)
        if (version < 1) {
            opendialog()
        }

        isRoomCreated()
    }




    fun isRoomCreated(){

        var MyRoomName =
            PreferenceManager.getDefaultSharedPreferences(this@MenuActivity)
                .getString("MyRoomName", "").toString()

        if(MyRoomName==""){

            createMy.visibility=View.VISIBLE
            enterMy.visibility=View.GONE

        }else{
            createMy.visibility=View.GONE
            enterMy.visibility=View.VISIBLE

        }





    }

    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@MenuActivity, 0, toolbar1)
        getWindow().getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        isRoomCreated()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event != null) {
            if(keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN){
                if((System.currentTimeMillis()-exitTime) > 2000){
                    Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis()
                } else {
                    val startMain = Intent(Intent.ACTION_MAIN)
                    startMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startMain.addCategory(Intent.CATEGORY_HOME)
                    startActivity(startMain)
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    // 获取软件的版本号 versionCode,用于比较版本
    private fun getLocalVersion(ctx: Context): Int {
        var localVersion = 0
        try {
            val packageInfo = ctx.applicationContext
                .packageManager
                .getPackageInfo(ctx.packageName, 0)
            localVersion = packageInfo.versionCode
            Log.d("TAG", "本软件的版本号。。$localVersion")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return localVersion
    }

    //打开更新弹窗
    private fun opendialog() {
        println("要更新")
        //如果版本低,弹出更新弹窗
        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (shadowFragment == null) {
            shadowFragment = BackgroundFragment.newInstance()
            mTransaction.add(fram!!.id, shadowFragment!!)
        }
        if (updateTips == null) {
            updateTips =
                UpdateTipsFrag.newInstance(this@MenuActivity)
            mTransaction.add(fram!!.id, updateTips!!)
        }
        mTransaction.commit()

    }
    //关闭弹窗
    private fun closeAlertDialog() {
        val mTransaction = supportFragmentManager.beginTransaction()
        if (updateTips != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(updateTips!!)
            updateTips = null
        }

        if (shadowFragment != null) {
            mTransaction.setCustomAnimations(
                R.anim.fade_in_out, R.anim.fade_in_out
            )
            mTransaction.remove(shadowFragment!!)
            shadowFragment = null
        }
        mTransaction.commit()
    }
    //不更新
    override fun cancelUpdateClick() {
        closeAlertDialog()
        finish()
    }
    //要更新
    override fun defineClick(downloadUrl: String) {
        val uri = Uri.parse(downloadUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
        closeAlertDialog()
    }
    //点击屏幕
    override fun clickAll() {
    }

}
