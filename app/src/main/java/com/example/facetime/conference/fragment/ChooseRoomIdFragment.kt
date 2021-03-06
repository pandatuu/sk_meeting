package com.example.facetime.conference.fragment

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
import click
import com.example.facetime.R
import com.example.facetime.conference.view.EnteRoomByIdActivity
import org.jetbrains.anko.*
import org.json.JSONArray
import org.json.JSONObject
import withTrigger

class ChooseRoomIdFragment : Fragment() {

    private var mContext: Context? = null
    private lateinit var verticalLayout:LinearLayout
    var flag=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
    }

    companion object {
        fun newInstance( flag:Int): ChooseRoomIdFragment {
            var f=ChooseRoomIdFragment()
            f.flag=flag
            return f
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
                    rightMargin = dip(15)
                    leftMargin = dip(15)
                }
            }
        }

        var usedRoom =
            PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString("usedRoom","[]")
        println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
        val array= JSONArray(usedRoom)

        for(i in 0 until  array!!.length()){
            verticalLayout.addView(addMyChild((array[array!!.length()-1-i] as JSONObject).getString("num"),(array[array!!.length()-1-i] as JSONObject).getString("name")))

        }


        return view
    }


    fun addMyChild(t:String,name:String): View {

        println(name)


        val view = with(mContext!!) {
            verticalLayout {
                linearLayout() {


                    this.withTrigger().click  {



                        if(flag==1){
                            (activity as EnteRoomByIdActivity).selectRoomToEditText(t)
                        }else{
                            (activity as EnteRoomByIdActivity).selectRoomNameToEditText(name)
                        }

                    }

                    backgroundResource=R.drawable.border_bottom

                    textView {
                        if(flag==2){
                            visibility=View.GONE
                        }
                        gravity = Gravity.CENTER
                        text = t
                        textColor=Color.BLACK
                        textSize=14f
                    }.lparams() {
                        width = dip(0)
                        height = matchParent
                        weight = 1f
                    }

                    textView {
                        if(flag==1){
                            visibility=View.GONE
                        }
                        gravity = Gravity.CENTER
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