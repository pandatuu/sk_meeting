package com.example.facetime.api

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface LoginApi {

    //登录
    @Headers("Content-Type: application/json")
    @POST("/api/users/login")
    fun userLogin(@Body array: RequestBody): Observable<Response<JsonObject>>

    //发送验证码
    @Headers("Content-Type: application/json")
    @POST("/api/users/verify-code")
    fun sendvCode(@Body array: RequestBody): Observable<Response<String>>

    //校验验证码
    @Headers("Content-Type: application/json")
    @POST("/api/users/validate-verify-code")
    fun validateCode(@Body array: RequestBody): Observable<Response<String>>

    //忘记密码
    @Headers("Content-Type: application/json")
    @PATCH("/api/users/find-password")
    fun findPassword(@Body array: RequestBody): Observable<Response<String>>

    //更新密码
    @Headers("Content-Type: application/json")
    @PATCH("api/users/change-password")
    fun updatePassword(@Body array: RequestBody): Observable<Response<String>>
}