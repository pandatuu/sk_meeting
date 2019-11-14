package com.example.facetime.setting.view

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.facetime.R
import org.jetbrains.anko.*

class UpdateTipsFrag : Fragment() {

    lateinit var mContext: Context
    private lateinit var buttomCLick : ButtomCLick
    private lateinit var imageV : ImageView

    companion object {
        fun newInstance(
            context: Context
        ): UpdateTipsFrag {
            val fragment = UpdateTipsFrag()
            fragment.mContext = context
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        buttomCLick = activity as ButtomCLick
        val view = createV()

        Glide.with(this@UpdateTipsFrag)
            .asBitmap()
            .load("")
            .into(imageV)
        return view
    }

    private fun createV(): View? {
        return with(mContext!!){
            linearLayout {
                gravity = Gravity.CENTER
                linearLayout {
                    isClickable = true
                    orientation = LinearLayout.VERTICAL
                    backgroundResource = R.drawable.fourdp_white_dialog
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        imageV = imageView {
                            adjustViewBounds = true
                            scaleType = ImageView.ScaleType.FIT_XY
                        }.lparams(wrapContent, dip(110)){
                            gravity = Gravity.CENTER_HORIZONTAL
                        }
                        scrollView {
                            isVerticalScrollBarEnabled = false
                            textView {
                                text = "111111111111111"
                                textSize = 13f
                                setLineSpacing(2f,1f)
                            }.lparams(wrapContent, wrapContent)
                        }.lparams(matchParent, dip(0)){
                            leftMargin = dip(15)
                            rightMargin = dip(10)
                            topMargin = dip(5)
                            weight = 1f
                        }
                    }.lparams(matchParent,dip(0)){
                        weight = 1f
                    }
                    relativeLayout {
                        button {
                            text = "キャンセル"
                            textSize = 16f
                            textColor = Color.WHITE
                            backgroundResource = R.drawable.button_shape_grey
                            setOnClickListener {
                                buttomCLick.cancelUpdateClick()
                            }
                        }.lparams(dip(120),dip(40)){
                            topMargin = dip(20)
                            alignParentLeft()
                        }
                        button {
                            text = "確定"
                            textSize = 16f
                            textColor = Color.WHITE
                            backgroundResource = R.drawable.yellow_background
                            setOnClickListener {
                                buttomCLick.defineClick("https://www.baidu.com")
                            }
                        }.lparams(dip(120),dip(40)){
                            topMargin = dip(20)
                            alignParentRight()
                        }
                    }.lparams(matchParent,dip(93)){
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }.lparams(matchParent,dip(400)){
                    leftMargin = dip(38)
                    rightMargin = dip(38)
                }
            }
        }
    }

    interface ButtomCLick{
        fun cancelUpdateClick()
        fun defineClick(downloadUrl: String)
    }
}