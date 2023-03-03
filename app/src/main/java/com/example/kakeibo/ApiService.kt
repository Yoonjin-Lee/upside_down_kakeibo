package com.example.kakeibo

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
        @Body goal : ServerGoalData ) : Call<Void>

    @GET("/expenditure/expenditures")
    fun getExpenditures(
        @Query("historyId") historyId : Int ) : Call<List<ServerExpenditureData>>


//    //Progress Bar
//    @GET("/goal/progressbar")
//    fun getProgressBar():Call<Int>

//    //특정 유저 오늘 사용 가능한 금액 조회
//    @GET("/goal/daily-avail-amount")
//    fun getDailyAvailAmount(
//        @Query("goal_id") goal_id : Int,
//        @Query("user_id") user_id : Int):Call<Data_DailyAvailable>

    //저축 가능한 금액 불러오기
    @GET("/goal/rest-amount")
    fun getRestAmount(
        @Query("goal_id") goal_id : Int):Call<Long>

//    //지출 내역 추가하기
//    @POST("/expenditure")
//    fun createDailyExpenditure(
//
//    )
}