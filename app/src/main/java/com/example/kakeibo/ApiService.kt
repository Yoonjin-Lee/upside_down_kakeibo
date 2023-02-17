package com.example.kakeibo

import android.media.session.MediaSession.Token
import com.navercorp.nid.oauth.NidOAuthLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

interface ApiService {
    //history fragment 정보 받기
    @GET("/history/list")
    fun getHistoryData():Call<List<ServerHistoryData>>

    //네이버 로그인
    @GET("/user/login")
    fun loginNaver(
        @Query("email") email : String?,
        @Query("name") name : String?) : Call<String>

    @POST("/goal")
    fun postGoalData(
//        @Query("icon") icon : Int?,
//        @Query("target_amount") target_amount : Int?,
//        @Query ("period_start") period_start : String?,
//        @Query ("period_end") period_end : String?,
//        @Query ("memo") memo : String?,
//        @Query ("monthly_income") monthly_income : Int?,
//        @Query ("essential_amount") essential_amount : Int?,
//        @Query ("rest_amount") rest_amount : Int?,
//        @Query ("daily_avail_amount") daily_avail_amount : Int?) : Call<Void>
        @Body goal : ServerGoalData ) : Call<Void>
}