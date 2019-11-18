package com.example.facetime.conference.api;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RoomApi {


    @POST("/api/v1/room-manage/")
    Observable<Response<JsonObject>> createRoom(
                    @Body RequestBody array
            );

    @POST("/api/v1/room/join")
    Observable<Response<String>> joinRoom(
            @Body RequestBody array
    );

    @GET("/api/v1/room/search")
    Observable<Response<JsonObject>> searchRoom(
            @Query("id") String id,
            @Query("name") String name
    );





}
