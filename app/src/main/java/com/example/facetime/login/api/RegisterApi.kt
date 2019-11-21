package com.example.facetime.login.api

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RegisterApi {

    //发送验证码
    @Headers("Content-Type: application/json")
    @POST("/api/users/verify-code")
    fun sendvCode(@Body array: RequestBody): Observable<Response<String>>

    //校验验证码
    @Headers("Content-Type: application/json")
    @POST("/api/users/validate-verify-code")
    fun validateCode(@Body array: RequestBody): Observable<Response<String>>

    //注册用户
    @Headers("Content-Type: application/json")
    @POST("/api/users/register")
    fun userRegister(@Body array: RequestBody): Observable<Response<JsonObject>>
}