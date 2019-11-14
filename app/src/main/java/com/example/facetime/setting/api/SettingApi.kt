package com.example.facetime.setting.api

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SettingApi {

    //更新密码
    @Headers("Content-Type: application/json")
    @PATCH("api/users/change-password")
    fun updatePassword(@Body array: RequestBody): Observable<Response<String>>

    //更新昵称
    @Headers("Content-Type: application/json")
    @PUT("api/v1/user/")
    fun updateNickName(@Body array: RequestBody): Observable<Response<String>>

    //登出
    @Headers("Content-Type: application/json")
    @POST("api/users/logout")
    fun logout(@Query("platform") platform: String): Observable<Response<String>>
}