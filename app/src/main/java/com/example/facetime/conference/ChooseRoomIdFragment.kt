package com.example.facetime.conference

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.facetime.R
import org.jetbrains.anko.*

class ChooseRoomIdFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var verticalLayout:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance(): ChooseRoomIdFragment {
            return ChooseRoomIdFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = createView()
        mContext = activity
        return fragmentView
    }

    private fun createView(): View {
        val view = with(mContext!!) {
            verticalLayout {
                scrollView {
                    backgroundResource= R.drawable.border_rectangle
                    verticalLayout= verticalLayout {


                    }.lparams() {
                        height = wrapContent
                        width = matchParent
                    }
                    bottomPadding=dip(1)

                }.lparams() {
                    height = dip(150)
                    width = matchParent
                }
            }
        }

        var usedRoomNum =
            PreferenceManager.getDefaultSharedPreferences(mContext)
                .getStringSet("usedRoomNum",setOf<String>())
        var it=usedRoomNum?.iterator()
        for(i in 0 until  usedRoomNum!!.size){
            verticalLayout.addView(addMyChild(it!!.next(),""))

        }


        return view
    }


    fun addMyChild(t:String,name:String): View {

        val view = with(mContext!!) {
            verticalLayout {
                linearLayout() {


                    setOnClickListener {

                        (activity as EnteRoomByIdActivity).selectRoomToEditText(t)


                    }

                    backgroundResource=R.drawable.border_bottom

                    textView {
                        gravity = Gravity.CENTER

                        text = t
                        textColor=Color.BLACK
                        textSize=14f
                    }.lparams() {
                        width = matchParent
                        height = matchParent
                    }

                    textView {
                        gravity = Gravity.CENTER or Gravity.RIGHT
                        text = name
                        textColor=Color.BLACK
                        textSize=14f


                    }.lparams() {
                        width = dip(0)
                        height = matchParent
                        weight = 1f
                    }

                }.lparams() {
                    height = dip(50)
                    width = matchParent
                    leftMargin = dip(10)
                    rightMargin = dip(10)
                }
            }
        }

        return view

    }
}