package com.example.facetime.util


import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View

import org.jetbrains.anko.*


open class CommonActivity  {




    companion object {
        fun addMyChild(mContext:Context): View {
            val view = with(mContext!!) {
                verticalLayout {
                    linearLayout() {

                       backgroundResource= com.example.facetime.R.drawable.border_transparent

                        textView {
                            gravity = Gravity.CENTER or Gravity.LEFT
                            textColor=Color.RED
                            textSize=16f
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams() {
                            width = dip(200)
                            height = matchParent
                        }

                    }.lparams() {
                        height = dip(50)
                        width = dip(200)
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
