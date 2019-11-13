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

    fun getToken():String?{
        return  mPerferences.getString("token", "")
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

                if(accessToken!=null && accessToken.isBlank()){
                    request.addHeader(
                        "Authorization",
                        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI1ZmEyYTYxOC04ZWRhLTQ2MmItOWQ2OC0wYWViM2VlYzc3MWMiLCJ1c2VybmFtZSI6Ijg2MTgwMTE0NTAxNjkiLCJ0aW1lc3RhbXAiOjE1NzMyMDgwNzUxMTcsImRldmljZVR5cGUiOiJBTkRST0lEIiwiaWF0IjoxNTczMjA4MDc1fQ.rO63KA0-Rgr1IUyKA0fGYoi4_cUJ2WSki0iP-XBnswGJVN00_YFeBIaK_53e46YvzJ3Qrt2hwhjEjk2QiUUmfarWKGOVPbNjghkLOTwUTQthyaVAgsGUleXCe3YFn0qzN9UXlq1BP4iXMGjMiVIntMevFiVI-bJMjI7Dyg2OJzSUhYY-82OLP0o3Da9PWmv1ZXw3hLejxcJBu70shirTb5kLlYZ_as3kqflklhO5MUhgBJS8zvG1QVHFb6_MKMhA5jOXikL2JB7nL8DiCZ8XJ0F3sL_hC46iy-ewA9G_33fv-xywPuELt2I1zDQlCKmQTIotRB68bS2RkBXxwFl4Rg"
                    )
                }



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
