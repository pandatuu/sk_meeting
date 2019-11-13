package com.example.facetime.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.orhanobut.logger.Logger
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitUtils(
    context: Context, //    private String baseUrl = "https://auth.sk.cgland.top/";
    baseUrl: String
) {


    companion object {


        private lateinit var retrofit: Retrofit

    }

    fun getToken():String{
        return mPerferences.getString("token", "").toString()
    }


    private val mPerferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))

        val builder = OkHttpClient.Builder()
            .connectTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                println(chain.request())

                val accessToken = mPerferences.getString("token", "")



                if (accessToken != null) {
                    if(accessToken.isNotBlank()){
                        request.addHeader(
                            "Authorization",
                            "Bearer ${accessToken.replace("\"","")}")
                    }
                }

//                if(accessToken.isBlank()){
//                    request.addHeader(
//                        "Authorization",
//                        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhMjFjMzY2YS0xMDc4LTRhZWItOWRjMC04YmE5MmIzMzZhZDAiLCJ1c2VybmFtZSI6Ijg2MTUxMTAzMTcwMjciLCJ0aW1lc3RhbXAiOjE1NjQwMjI3MDEwNzMsImRldmljZVR5cGUiOiJBTkRST0lEIiwiaWF0IjoxNTY0MDIyNzAxfQ.eUPhccetHnmsJw2b-iP6IZdbJBOogYj43jIiXF8t8kUhmThBpPLmKuM1F_Jdq51vPAH7jogrN9GsXE8IhhcpYwefVCAbNkaKzn-EeF5VmavHCurZhVZ9vvg8fOdcOOkdEncrW3nvfgK9LmpRYONywvxYdzk6TEvJp9v-NnrfqWY2KVRLkur_SwLtaiG_rmrLbvtmfYkkT6Tg_sZl5RZ9gVuwUOu1YHgpFGF09OUJHTRzt_llNtnSXrI23fc1T5FgzxcpOILXsQWZ52G5N3GT7BzVBxOanXVbNTN6LAaY_dzhTeU1NsaRZ48-e3pe0zzohej-rm7zj0YKBrxmtmnDeA")
////                }



                println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                println(request.build().headers().get("Authorization"))
                //添加截器
                chain.proceed(request.build())
            }

        val loggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Logger.i(message) }).setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        builder.addInterceptor(loggingInterceptor)

        retrofit = retrofitBuilder.client(builder.build()).build()
    }

    fun <T> create(cls: Class<T>): T {
        return retrofit.create(cls)
    }

}
