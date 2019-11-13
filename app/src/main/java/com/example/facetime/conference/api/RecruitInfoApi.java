package com.example.facetime.conference.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RecruitInfoApi {


    //得到职位信息列表
    //http://organization-position.sk.cgland.top
    //@Headers("Content-Type: application/json")
    @GET("/api/organization-positions/user")
    Observable<JsonObject> getRecruitInfoList(
            @Query("_page") Integer _page,
            @Query("_limit") Integer _limit,
            @Query("organization-id") String organizationId,
            @Query("name") String name,
            @Query("recruit-method") String recruitMethod,
            @Query("working-type") String workingType,
            @Query("working-experience") Integer workingExperience,
            @Query("currency-type") String currencyType,
            @Query("salary-type") String salaryType,
            @Query("salary-min") Integer salaryMin,
            @Query("salary-max") Integer salaryMax,
            @Query("auditState") String auditState,
            @Query("educational-background") String educationalBackground,
            @Query("organization-industry-id") String organizationIndustryId,
            @Query("area-id") String address,
            @Query("radius") Number radius,
            @Query("financing-stage") String financingStag,
            @Query("size") String size,
            @Query("industry-id") String jobWantedIndustryId,
            @Query("organization-category") String organizationCategory

    );

    //根据Id查询公司详细信息
    @GET("/api/organizations/{organization-id}")
    Observable<JsonObject> getCompanyInfo(
            @Path("organization-id") String organizationId
    );




    //查询中间过度列表
    @GET("/api/organization-positions/position-list")
    Observable<JsonArray> getRecruitInfoMiddleList(
            @Query("name") String name
    );



    //根据Id查询职位信息
    @GET("/api/organization-positions/{id}")
    Observable<JsonObject> getRecruitInfoById(
            @Path("id") String id
    );





}
