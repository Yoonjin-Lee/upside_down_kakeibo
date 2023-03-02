package com.example.kakeibo

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://ekh-be2.shop"
//const val BASE_URL = "http://172.30.1.91:4973"

//fun getRetrofit(): Retrofit {
//    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create()).build()
//
//    return retrofit
//}

private var instance: Retrofit? = null
private val gson = GsonBuilder().setLenient().create()

// timeout시간을 설정해줍니다.
//private const val CONNECT_TIMEOUT_SEC = 20000L

fun getRetrofit(): Retrofit {
    if (instance == null) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .addInterceptor(interceptor)
//            .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .build()

        // 이 부분이 없어서 no json document 에러가 났던 거였음...
        val gson = GsonBuilder().setLenient().create()

        instance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    return instance!!
}
