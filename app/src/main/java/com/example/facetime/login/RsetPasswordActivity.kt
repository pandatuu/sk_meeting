package com.example.facetime.login

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.facetime.R
import org.jetbrains.anko.*




class RsetPasswordActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayout {
            backgroundColor = Color.parseColor("#F2F2F2")
            verticalLayout {
                backgroundColor = Color.parseColor("#F2F2F2")

                linearLayout {
                    imageView {
                        imageResource = R.mipmap.icon_back
                    }.lparams(width = wrapContent,height = wrapContent){
                        rightMargin = dip(5)
                        gravity = Gravity.CENTER
                    }
                    textView {
                        text = "返回"
                        textSize = 16f
                        textColor = Color.parseColor("#7F7F7F")
                    }.lparams(width = wrapContent,height = wrapContent){
                        gravity = Gravity.CENTER
                    }

                    this.setOnClickListener(View.OnClickListener {
                        // TODO Auto-generated method stub
                        finish()
                    })
                }.lparams(width = matchParent,height = wrapContent){
                    topMargin = dip(20)
                    gravity = Gravity.LEFT
                }

                textView {
                    text = "重置密码"
                    gravity = Gravity.CENTER
                    textSize = 21f
                    textColor = Color.parseColor("#333333")
                }.lparams(height = wrapContent,width = matchParent){
                    topMargin = dip(75)
                }

                linearLayout {
                    backgroundResource = R.drawable.input_border
                    editText {
                        hint = "请输入新密码"
                        backgroundColor = Color.WHITE
                    }.lparams(width = matchParent,height = wrapContent){
                        weight = 1f
                    }
                }.lparams(height = wrapContent,width = matchParent){
                    topMargin = dip(25)
                }

                linearLayout {
                    backgroundResource = R.drawable.input_border
                    textView {

                    }.lparams(width = wrapContent,height = matchParent){
                        topMargin = dip(10)
                    }

                    editText {
                        hint = "请再次输入新密码"
                        backgroundColor = Color.WHITE
                    }.lparams(width = matchParent,height = wrapContent){
                        weight = 1f
                    }
                }.lparams(height = wrapContent,width = matchParent){
                    topMargin = dip(10)
                    leftPadding = dip(10)
                    rightPadding = dip(10)
                }


                button {
                    backgroundResource = R.drawable.login_button
                    text = "完成"
                    textColor = Color.WHITE
                    textSize = 21f
                }.lparams(width = matchParent,height = wrapContent){
                    topMargin = dip(50)
                }
            }.lparams(width = matchParent,height = matchParent){
                leftMargin = dip(20)
                rightMargin = dip(20)
            }
        }
    }
}