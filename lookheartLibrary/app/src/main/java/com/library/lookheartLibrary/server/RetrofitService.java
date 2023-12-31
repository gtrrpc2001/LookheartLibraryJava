package com.library.lookheartLibrary.server;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    // --------------------------- GET --------------------------- //

    @GET("msl/findID?")
    Call<String> findID(@Query("eqname") String name, @Query("phone") String phoneNumber, @Query("birth") String birthday);

    // checkLogin
    @GET("msl/CheckLogin")
    Call<String> checkLogin(@Query("empid") String empid, @Query("pw") String pw);

    // checkID
    @GET("msl/CheckIDDupe")
    Call<String> checkID(@Query("empid") String empid);

    // getProfile
    @GET("msl/Profile")
    Call<List<UserProfile>> getProfileData(@Query("empid") String empid);


    @GET("/mslecgarr/arrWritetime?")
    Call<String> getArrList(@Query("eq") String eq, @Query("startDate") String startDate, @Query("endDate") String endDate);


    @GET("mslecgarr/arrPreEcgData?")
    Call<String> getArrData(@Query("eq") String eq, @Query("date") String date);

    // --------------------------- POST --------------------------- //

    // tenSecondData
    @POST("mslbpm/api_data")
    Call<String> sendTenSecondData(@Body Map<String, Object> data);

    // hourlyData
    @POST("mslecgday/api_getdata")
    Call<String> sendHourlyData(@Body Map<String, Object> data);

    // EcgData
    @POST("mslecg/api_getdata")
    Call<String> sendEcgData(@Body Map<String, Object> data);

//    @POST("mslecgbyte/api_getdata")
//    Call<String> sendEcgData(@Body Map<String, Object> data);
    // ArrData
    @POST("mslecgarr/api_getdata")
    Call<String> sendArrData(@Body Map<String, Object> data);

    // setProfile And signup
    @POST("msl/api_getdata")
    Call<String> setProfile(@Body Map<String, Object> data);

    // guardian
    @POST("mslparents/api_getdata")
    Call<String> setGuardian(@Body Map<String, Object> data);

    // updatePWD
    @POST("msl/api_getdata")
    Call<String> updatePWD(@Body Map<String, Object> data);
}
