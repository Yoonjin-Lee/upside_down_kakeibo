package com.example.kakeibo

import android.media.session.MediaSession.Token
import com.navercorp.nid.oauth.NidOAuthLogin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    //history fragment 정보 받기
    @GET("/history/list")
    fun getHistoryData():Call<List<ServerHistoryData>>

    //네이버 로그인
    @GET("/oauth2/authorization/naver")
    fun naverLogin():Call<String>
}