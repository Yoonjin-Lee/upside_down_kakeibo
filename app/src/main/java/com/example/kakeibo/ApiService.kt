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
}