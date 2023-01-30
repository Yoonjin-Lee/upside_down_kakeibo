package com.example.kakeibo

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/history/list")
    fun getHistoryData():Call<List<ServerHistoryData>>
}