package com.example.kakeibo

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.databinding.FragmentHistoryBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class HistoryFragment : Fragment() {
    private lateinit var viewBinding: FragmentHistoryBinding
    var serverData : String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHistoryBinding.inflate(layoutInflater)

        //데이터를 넘겨줄 리스트
        val weekDataList : ArrayList<WeekData> = arrayListOf()
        val adapter = WeekRAdapter(weekDataList)

        //어뎁터, 수직 설정
        viewBinding.list.adapter = adapter
        viewBinding.list.layoutManager = LinearLayoutManager(context)

        //API
        fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {

                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { hostname, session -> true }

            return builder
        }

        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.1.82:1108")
//            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(getUnsafeOkHttpClient().build()) //우회
            .build()

        val apiService = retrofit.create(ApiService::class.java)

//        apiService.getHistoryData().enqueue(object : Callback<String> {
//            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
//                if (response.isSuccessful) {
//                    val data = response.body()
//
//                    if (data != null) {
//                        Log.d("retrofit", "열차 정보 :" + data)
//
//                        val date_num = data.length
//                        val week_num = date_num/7
//                        Log.d("date_num", date_num.toString())
//                        Log.d("week_num", week_num.toString())
//
//                        for (i in 1 until week_num + 1){
//                            for (j in data){
//                                //i = 주차
//                                val dateData = j
//                                Log.d("dateData", dateData.toString())
//                                Log.d("check", "1")
//                            }
//                        }
//                    }
//                } else {
//                    Log.w("retrofit", "실패 ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Log.w("retrofit", "열차 정보 접근 실패", t)
//                Log.w("retrofit", "열차 정보 접근 실패 response",)
//            }
//        })

        // test
        val authService = getRetrofit().create(ApiService::class.java)

        authService.getHistoryData().enqueue(object : Callback<List<ServerHistoryData>> {
            override fun onResponse(call: Call<List<ServerHistoryData>>, response: retrofit2.Response<List<ServerHistoryData>>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    if (data != null) {
                        Log.d("test_retrofit", "열차 정보 :" + data)

                        val date_num = data.size
                        val week_num = date_num/7
                        Log.d("date_num", date_num.toString())
                        Log.d("week_num", week_num.toString())

                        for (i in 1 until week_num + 1){
                            for (j in data){
                                //i = 주차
                                val dateData = j
                                Log.d("dateData", dateData.toString())
                                Log.d("check", "1")
                            }
                        }
                    }
                } else {
                    Log.w("retrofit", "실패 ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ServerHistoryData>>, t: Throwable) {
                Log.w("retrofit", "열차 정보 접근 실패", t)
                Log.w("retrofit", "열차 정보 접근 실패 response",)
            }
        })

        //테스트 데이터
        adapter.apply {
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20",1,1,1,1, 1, 1,1, "50"))
        }

        //리스트 아이템 사이 간격
        val verticalItemDecorator = VerticalItemDecorator(16)
        viewBinding.list.addItemDecoration(verticalItemDecorator)

        //shareBtn clickListener 구현
        adapter.setItemClickListener(object : WeekRAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                val intent = Intent(context, ResultActivity::class.java)
                startActivity(intent)
            }
        })

        //날짜 버튼 ClickListener 구현
        adapter.setDateClickListener(object : WeekRAdapter.OnDateClickListener{
            override fun onClick(v: View, position: Int) {
                val intent = Intent(context, DateresultActivity::class.java)
                startActivity(intent)
            }
        })

        return viewBinding.root
    }

    //아이템 간격을 위한 클래스
    inner class VerticalItemDecorator(var divHeight: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.top = divHeight
            outRect.bottom = divHeight
        }
    }
}