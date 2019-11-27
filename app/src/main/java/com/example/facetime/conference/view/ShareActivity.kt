package com.example.facetime.conference.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXTextObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMWeb
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.button
import org.jetbrains.anko.linearLayout


class ShareActivity : AppCompatActivity() {

    lateinit var qq: Button
    lateinit var wx: Button
    lateinit var dd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UMConfigure.setLogEnabled(true)
        UMConfigure.init(
            this, "5cdcc324570df3ffc60009c3"
            , "umeng", UMConfigure.DEVICE_TYPE_PHONE, ""
        )

        linearLayout {
            backgroundColor = Color.WHITE
            qq = button {
                text = "qq"
                setOnClickListener {
                    PlatformConfig.setQQZone("1110022340", "Y1q9LJRhykp44N4j")
                    val shareListener = object : UMShareListener {
                        override fun onStart(share_media: SHARE_MEDIA) {

                        }

                        override fun onResult(share_media: SHARE_MEDIA) {

                        }

                        override fun onError(share_media: SHARE_MEDIA, throwable: Throwable) {

                        }

                        override fun onCancel(share_media: SHARE_MEDIA) {

                        }
                    }
                    ShareAction(this@ShareActivity)
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台;
                        .withText("this is chat App,welcome to try")
                        .withMedia(UMWeb("https://www.baidu.com"))
                        .setCallback(shareListener)
                        .share()
                }
            }

            wx = button {
                text = "wx"
                setOnClickListener {
                    val APP_ID = "wxa734d668789e6b82"
                    val api = WXAPIFactory.createWXAPI(this@ShareActivity, APP_ID, true)

                    api.registerApp(APP_ID)
                    //初始化一个 WXTextObject 对象，填写分享的文本内容
                    val textObj = WXTextObject()
                    textObj.text = "111111111111"

                    //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
                    val msg = WXMediaMessage()
                    msg.mediaObject = textObj
                    msg.description = "11111111111"

                    val req = SendMessageToWX.Req()
                    req.transaction =
                        System.currentTimeMillis().toString()  //transaction字段用与唯一标示一个请求
                    req.message = msg

//                WXEntryActivity wx = new WXEntryActivity();
//                api.handleIntent(getIntent(), wx);
                    //调用api接口，发送数据到微信
                    api.sendReq(req)
                }
            }

            dd = button {
                text = "dingding"
                setOnClickListener {
                    val shareListener = object : UMShareListener {
                        override fun onStart(share_media: SHARE_MEDIA) {

                        }

                        override fun onResult(share_media: SHARE_MEDIA) {

                        }

                        override fun onError(share_media: SHARE_MEDIA, throwable: Throwable) {

                        }

                        override fun onCancel(share_media: SHARE_MEDIA) {

                        }
                    }
                    PlatformConfig.setDing("dingoamq8ymfaatukyfhgl")
                    ShareAction(this@ShareActivity)
                        .setPlatform(SHARE_MEDIA.DINGTALK)//传入平台
                        .withText("this is chat App,welcome to try")
                        .setCallback(shareListener)
                        .share()
                }
            }
        }
        val WRITE = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val READ = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (READ == PackageManager.PERMISSION_GRANTED && WRITE == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this@ShareActivity, "hava this permission", Toast.LENGTH_SHORT)
                .show()
            share()
        } else {
            Toast.makeText(this@ShareActivity, "no this permission", Toast.LENGTH_SHORT).show()
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
        }
    }

    private fun share() {
        qq.setOnClickListener {
            //APP ID1110022340  APP KEYY1q9LJRhykp44N4j
//            val image = UMImage(this@MainActivity, "https://d2k70mtsylip4r.cloudfront.net/733d1e2a-5166-41e8-942c-1e6b60e13061.jpg")
            PlatformConfig.setQQZone("1110022340", "Y1q9LJRhykp44N4j")
            ShareAction(this)
                .setPlatform(SHARE_MEDIA.QQ)//传入平台
                .withText("this is chat App,welcome to try")
                .withMedia(UMWeb("https://www.baidu.com"))
                .setCallback(object : UMShareListener {
                    override fun onResult(p0: SHARE_MEDIA?) {

                    }

                    override fun onCancel(p0: SHARE_MEDIA?) {

                    }

                    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                        println("qq-------${p1?.message}")
                        Toast.makeText(this@ShareActivity, "QQ失败", Toast.LENGTH_SHORT).show()
                    }

                    override fun onStart(p0: SHARE_MEDIA?) {

                    }
                })
                .share()
        }
        wx.setOnClickListener {
            val APP_ID = "wxa734d668789e6b82"
            val api = WXAPIFactory.createWXAPI(this, APP_ID, true);

            api.registerApp(APP_ID)
            //初始化一个 WXTextObject 对象，填写分享的文本内容
            val textObj = WXTextObject()
            textObj.text = "111111111111"

            //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
            val msg = WXMediaMessage()
            msg.mediaObject = textObj
            msg.description = "11111111111"

            val req = SendMessageToWX.Req()
            req.transaction = System.currentTimeMillis().toString()  //transaction字段用与唯一标示一个请求
            req.message = msg
            // SendMessageToWX.Req.WXSceneSession是分享到好友会话
            // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
//            req.scene = SendMessageToWX.Req.WXSceneSession;

            //调用api接口，发送数据到微信
            api.sendReq(req)

        }
        dd.setOnClickListener {
            PlatformConfig.setDing("dingoamq8ymfaatukyfhgl")
            ShareAction(this)
                .setPlatform(SHARE_MEDIA.DINGTALK)//传入平台
                .withText("this is chat App,welcome to try")
                .setCallback(object : UMShareListener {
                    override fun onResult(p0: SHARE_MEDIA?) {
                        println("dinggggggggggg " + p0)

                    }

                    override fun onCancel(p0: SHARE_MEDIA?) {
                        println("dinggggggggggg " + p0)

                    }

                    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                        println("dingding-------${p1?.message}")
                        Toast.makeText(this@ShareActivity, "失败", Toast.LENGTH_SHORT).show()
                    }

                    override fun onStart(p0: SHARE_MEDIA?) {

                    }
                })
                .share()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        println("$requestCode----$permissions----$grantResults")
        if (requestCode == 123) {
            share()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}