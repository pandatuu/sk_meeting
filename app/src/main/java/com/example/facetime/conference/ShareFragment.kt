package com.example.facetime.conference

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.facetime.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ShareFragment : androidx.fragment.app.Fragment() {

    private var mContext: Context? = null

    lateinit var layout: LinearLayout

    lateinit var sharetDialogSelect: SharetDialogSelect


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

    }

    companion object {
        fun newInstance( ): ShareFragment {
            var f = ShareFragment()
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        mContext = activity
        sharetDialogSelect = activity as SharetDialogSelect
        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    private fun createView(): View {

        val view = with(mContext!!) {
            linearLayout {
                gravity = Gravity.BOTTOM

                verticalLayout {

                    setOnClickListener ( object :View.OnClickListener{
                        override fun onClick(v: View?) {
                        }
                    } )
                    backgroundColor= Color.WHITE
                    textView {
                        text = "共有"
                        gravity = Gravity.CENTER
                        textSize = 15f
                        textColorResource = R.color.black20
                        gravity = Gravity.CENTER
                        typeface = Typeface.defaultFromStyle(Typeface.BOLD)

                    }.lparams(width = matchParent, height = dip(60)) {

                    }

                    linearLayout(){

                        verticalLayout {
                            onClick { sharetDialogSelect.getSelectedItem(0) }
                            gravity=Gravity.CENTER_HORIZONTAL

                            imageView {
                                setImageResource(R.mipmap.line)

                            }.lparams {
                                height=dip(60)
                                width=dip(60)
                            }

                            textView {
                                text="Line"
                                textSize=14f
                                textColorResource= R.color.black20
                            }.lparams {
                                topMargin=dip(10)

                            }

                        }.lparams {
                            height= matchParent
                            width=dip(0)
                            weight=1f
                        }

                        verticalLayout {
                            onClick { sharetDialogSelect.getSelectedItem(1) }
                            gravity=Gravity.CENTER_HORIZONTAL

                            imageView {
                                setImageResource(R.mipmap.twitter)

                            }.lparams {
                                height=dip(60)
                                width=dip(60)
                            }


                            textView {
                                text="Twitter"
                                textSize=14f
                                textColorResource= R.color.black20
                            }.lparams {
                                topMargin=dip(10)

                            }

                        }.lparams {
                            height= matchParent
                            width=dip(0)
                            weight=1f
                        }

                    }.lparams {
                        width= matchParent
                        height=dip(110)
                    }



                    textView {
                        backgroundColorResource= R.color.grayE6
                    }.lparams {
                        height=dip(1)
                        width= matchParent
                        leftMargin=dip(15)
                        rightMargin=dip(15)
                    }
                    textView {
                        text = "キャンセル"
                        gravity = Gravity.CENTER
                        textSize = 15f
                        textColorResource = R.color.gray5c

                        onClick { sharetDialogSelect.getSelectedItem(-1) }
                    }.lparams(width = matchParent, height = dip(60)) {

                    }
                }.lparams(width = matchParent, height = wrapContent) {


                }
            }
        }


        return view
    }



    interface SharetDialogSelect {
        // 按下选项
        suspend fun getSelectedItem(index: Int)
    }


}