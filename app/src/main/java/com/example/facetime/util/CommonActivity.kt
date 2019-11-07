package com.example.facetime.util


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
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo.IME_ACTION_GO
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.example.facetime.R
import com.example.facetime.conference.ChooseRoomIdFragment
import com.example.facetime.conference.EnteRoomByIdActivity
import com.example.facetime.conference.JitsiMeetActivitySon
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import com.twitter.sdk.android.tweetcomposer.TweetComposer
import com.umeng.commonsdk.UMConfigure
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext
import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA
import java.lang.Thread.sleep
import java.util.*


open class CommonActivity  {




    companion object {
        fun addMyChild(mContext:Context): View {
            lateinit var textView:TextView
            val view = with(mContext!!) {
                verticalLayout {
                    linearLayout() {


                        backgroundResource= com.example.facetime.R.drawable.border

                        textView= textView {
                            gravity = Gravity.CENTER or Gravity.LEFT

                            textColor=Color.BLACK
                            textSize=14f





                        }.lparams() {
                            width = org.jetbrains.anko.wrapContent
                            height = org.jetbrains.anko.matchParent
                        }

                    }.lparams() {
                        height = dip(50)
                        width = org.jetbrains.anko.matchParent
                        leftMargin = dip(10)
                        rightMargin = dip(10)
                        topMargin=dip(10)
                    }
                }
            }

//            (mContext as JitsiMeetActivitySon).finish()
//            (mContext as JitsiMeetActivitySon).overridePendingTransition(R.anim.left_in, R.anim.right_out)

            return view

        }

    }





}
