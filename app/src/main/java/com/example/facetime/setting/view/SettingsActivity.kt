package com.example.facetime.setting.view


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

import com.facebook.react.modules.core.PermissionListener
import org.jitsi.meet.sdk.*
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import com.example.facetime.R


open class SettingsActivity : AppCompatActivity(), JitsiMeetActivityInterface {


    override fun requestPermissions(p0: Array<out String>?, p1: Int, p2: PermissionListener?) {

    }

    var useNew=false

    var defaultValue =getString(R.string.videoUrl)

    private lateinit var toolbar1: Toolbar
    lateinit var verticalLayout: LinearLayout
    lateinit var ms: SharedPreferences

    lateinit var editText: EditText
    var selectedAdd = ""

    var listImage = mutableListOf<ImageView>()
    var listText = mutableListOf<TextView>()
    var changeHappen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ms = PreferenceManager.getDefaultSharedPreferences(this@SettingsActivity)




        verticalLayout {
            backgroundResource = R.mipmap.company_bg

            setOnTouchListener { v, event ->

                closeSoftKeyboard(editText)
                editText.clearFocus()
                 false
            }

            relativeLayout() {


                //                imageView() {
//
//                    imageResource = R.mipmap.company_bg
//                    scaleType=(ImageView.ScaleType.CENTER_CROP)
//                    }.lparams() {
//                    width = matchParent
//                    height = dip(65)
//
//                }

                relativeLayout() {
                    setOnTouchListener { v, event ->

                        closeSoftKeyboard(editText)
                        editText.clearFocus()
                        false
                    }
                    toolbar1 = toolbar {
                        backgroundResource = R.color.transparent
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back_white

                    }.lparams() {
                        width = wrapContent
                        height = dip(65)
                        alignParentBottom()

                    }



                }.lparams() {
                    width = matchParent
                    height = dip(65)
                }
            }.lparams() {
                width = matchParent
                height = dip(65)
            }





            linearLayout() {

                setOnClickListener {
                    editText.requestFocus()
                    showSoftKeyboard(editText)

                }

                gravity = Gravity.CENTER
                backgroundResource = R.drawable.edittext_bg


                editText = editText() {
                    textColor = Color.WHITE
                    hint = "新的服务器地址"
                    setHintTextColor(Color.GRAY)
                    backgroundResource = R.drawable.edittext_bg
                    singleLine = true
                    imeOptions = IME_ACTION_DONE

                    addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {


                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                        }

                        override fun afterTextChanged(s: Editable?) {
                            if (text.toString() == "") {
                                hint = "新的服务器地址"
                            } else {
                                hint = ""
                            }
                        }

                    })

                    setOnFocusChangeListener { view, b ->

                        if (b) {
                            useNew=true

                            for (i in 0 until listImage.size) {

                                listImage.get(i).visibility = View.INVISIBLE
                                listText.get(i).textColor = Color.GRAY

                            }
                            setHintTextColor(Color.WHITE)
                            textColor = Color.WHITE
                        } else {
                            setHintTextColor(Color.GRAY)
                            textColor = Color.GRAY
                        }

                    }

                }.lparams() {

                    width = wrapContent
                    height = matchParent

                }


            }.lparams() {
                topMargin = dip(0)
                rightMargin = dip(15)
                leftMargin = dip(15)
                height = dip(50)
                width = matchParent
            }




            scrollView {

                backgroundColor=Color.TRANSPARENT
                setOnTouchListener { v, event ->

                    closeSoftKeyboard(editText)
                    editText.clearFocus()
                     false;
                }


//                setOnClickListener {
//                    editText.requestFocus()
//                    showSoftKeyboard(editText)
//
//                }
                verticalLayout = verticalLayout {


                }.lparams() {



                    width = matchParent
                    height = wrapContent
                }


            }.lparams() {
                topMargin = dip(20)
                bottomMargin= dip(10)

                height = 0
                weight = 1f
                width = matchParent
            }


            textView {
                text = "确认"
                textColor = Color.WHITE
                backgroundResource = R.drawable.bottonbg
                gravity = Gravity.CENTER
                textSize = 20F

                setOnClickListener {

                    if(useNew){
                        selectedAdd=editText.text.toString()
                    }


                    var selected =
                        PreferenceManager.getDefaultSharedPreferences(this@SettingsActivity)
                            .getString("selected", defaultValue).toString()

                    if (selected != selectedAdd) {
                        changeHappen = true
                        var mEditor: SharedPreferences.Editor = ms.edit()
                        mEditor.putString("selected", selectedAdd)

                        var selected =
                            PreferenceManager.getDefaultSharedPreferences(this@SettingsActivity)
                                .getStringSet("selectedSet",mutableSetOf(defaultValue) )
                        selected?.add(selectedAdd)
                        mEditor.putStringSet("selectedSet", selected)


                        mEditor.commit()
                    }


                    var mIntent = Intent()
                    if (changeHappen) {

                        mIntent.putExtra("ip", selectedAdd)
                    }
                    setResult(Activity.RESULT_OK, mIntent)
                    finish()
                    overridePendingTransition(
                        R.anim.left_in,
                        R.anim.right_out
                    )
                }
            }.lparams() {

                width = matchParent
                height = dip(50)
                bottomMargin = dip(10)
                rightMargin = dip(15)
                leftMargin = dip(15)
            }


        }


        initSelect()
    }


    fun initSelect() {

        var add =
            PreferenceManager.getDefaultSharedPreferences(this).getString("selected", defaultValue)
                .toString()
        if (add != selectedAdd) {
            var mEditor: SharedPreferences.Editor = ms.edit()
            mEditor.putString("selected", add)


            mEditor.commit()
        }

        selectedAdd = add


        var selected =
            PreferenceManager.getDefaultSharedPreferences(this@SettingsActivity)
                .getStringSet("selectedSet",mutableSetOf(defaultValue) )

        println("xxxxxx"+selected?.size)


        var it=selected?.iterator()


        while(it!!.hasNext()){
            addSon(it?.next())
        }


    }


    override fun onStart() {
        super.onStart()
        setActionBar(toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@SettingsActivity, 0, toolbar1)
        toolbar1.setNavigationOnClickListener {
            finish()//返回
            overridePendingTransition(
                R.anim.left_in,
                R.anim.right_out
            )
        }


    }


    fun addSon(t: String) {

        lateinit var imageView: ImageView
        lateinit var textView: TextView


        var b = false


        if (t == selectedAdd)
            b = true

        var view = UI {

            linearLayout {

                setOnClickListener {


                    closeSoftKeyboard(editText)
                    editText.clearFocus()


                    for (i in 0 until listImage.size) {

                        listImage.get(i).visibility = View.INVISIBLE
                        listText.get(i).textColor = Color.GRAY

                    }

                    imageView.visibility = View.VISIBLE
                    textView.textColor = Color.WHITE
                    selectedAdd = textView.text.toString()


                }


                linearLayout() {
                    gravity = Gravity.CENTER_VERTICAL

                    imageView = imageView() {
                        imageResource = R.mipmap.icon_select_home
                        if (!b) {
                            visibility = View.INVISIBLE
                        }
                    }.lparams() {
                        height = dip(20)
                        width = dip(20)

                    }

                    textView = textView {
                        gravity = Gravity.CENTER
                        text = t
                        textSize = 20f
                        textColor = Color.WHITE
                        if (!b) {
                            textColor = Color.GRAY
                        }


                    }.lparams() {
                        leftMargin = dip(10)
                    }


                }.lparams() {
                    width = matchParent
                    leftMargin = dip(50)
                    rightMargin = dip(15)
                    height = dip(50)
                }

            }

        }
        listImage.add(imageView)
        listText.add(textView)


        verticalLayout.addView(view.view)


    }

    fun closeSoftKeyboard(view: View?) {

        useNew=false

        if (view == null || view.windowToken == null) {
            return
        }
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.hideSoftInputFromWindow(view.windowToken, 0)


        var add =
            PreferenceManager.getDefaultSharedPreferences(this).getString("selected", defaultValue)
                .toString()
        for (i in 0 until listImage.size) {
            if(add==listText.get(i).text.toString()){
                listImage.get(i).visibility = View.VISIBLE
                listText.get(i).textColor = Color.WHITE
            }else{
                listImage.get(i).visibility = View.INVISIBLE
                listText.get(i).textColor = Color.GRAY
            }
        }


    }

    fun showSoftKeyboard(view: View?) {


        useNew=true

        for (i in 0 until listImage.size) {

            listImage.get(i).visibility = View.INVISIBLE
            listText.get(i).textColor = Color.GRAY

        }


        if (view == null || view.windowToken == null) {
            return
        }
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.showSoftInput(view, 0)
    }


}
