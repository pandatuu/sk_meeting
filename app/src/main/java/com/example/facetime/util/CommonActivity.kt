package com.example.facetime.util


import android.Manifest
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.example.facetime.R
import com.example.facetime.conference.fragment.BackgroundFragment
import com.example.facetime.conference.fragment.ShareFragment
import com.twitter.sdk.android.tweetcomposer.TweetComposer
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA

import org.jetbrains.anko.*


open class CommonActivity {


    companion object {
        lateinit var outer:LinearLayout

        fun addMyChild(mContext: Context): View {
            val view = with(mContext!!) {
                verticalLayout {
                    var theId=100
                    outer = linearLayout() {
                        id=theId
                        linearLayout() {

                            backgroundResource = com.example.facetime.R.drawable.border_transparent
                            //backgroundColor=Color.WHITE
                            textView {
                                gravity = Gravity.CENTER or Gravity.LEFT
                                textColor = Color.RED
                                textSize = 16f
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams() {
                                width = dip(200)
                                height = matchParent
                            }


                            textView {

                            }.lparams() {
                                width = dip(0)
                                height = matchParent
                                weight = 1f
                            }

                            linearLayout {

                                gravity = Gravity.CENTER
                                imageView {
                                    imageResource = R.mipmap.icon_share_zwxq

                                }.lparams(wrapContent, wrapContent) {

                                    rightMargin = dip(15)
                                }

                            }.lparams() {
                                height = matchParent
                                width = wrapContent
                            }

                        }.lparams() {
                            height = dip(50)
                            width = matchParent
                            leftMargin = dip(10)
                            rightMargin = dip(10)
                            topMargin = dip(10)
                        }


                    }.lparams() {
                        height = matchParent
                        width = matchParent
                    }

                }
            }

//            (mContext as JitsiMeetActivitySon).finish()
//            (mContext as JitsiMeetActivitySon).overridePendingTransition(R.anim.left_in, R.anim.right_out)

            return view

        }





    }



}
