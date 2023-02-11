package com.example.kakeibo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kakeibo.databinding.ActivityDateresultBinding
import retrofit2.Call
import retrofit2.Callback

class DateresultActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityDateresultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityDateresultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.backBtn.setOnClickListener {
            finish()
        }

        viewBinding.button.setOnClickListener {
            finish()
        }

        //리사이클러뷰에 데이터를 넘겨줄 리스트
        val dateList : ArrayList<DateData> = arrayListOf()
        val adapter = DateRAdapter(dateList)

        //리사이클러뷰 어뎁터 설정
        viewBinding.recyclerSpendList.adapter = adapter
        viewBinding.recyclerSpendList.layoutManager = LinearLayoutManager(this)

        //api 연결
        val authService = getRetrofit().create(ApiService::class.java)

//        authService.getHistoryData().enqueue(object : Callback<List<ServerHistoryData>> {
//            override fun onResponse(call: Call<List<ServerHistoryData>>, response: retrofit2.Response<List<ServerHistoryData>>) {
//                if (response.isSuccessful) {
//                    val data = response.body()
//
//                    if (data != null) {
//                        Log.d("test_retrofit", "특정일 소비 정보" + data)
//
//                    }
//                } else {
//                    Log.w("retrofit", "실패 ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<List<ServerHistoryData>>, t: Throwable) {
//                Log.w("retrofit", "특정일 소비 정보 접근 실패", t)
//            }
//        })

        adapter.apply {
            dateList.add(DateData(R.drawable.ic_halfselected_cafe, "카페", "아이스 아메리카노", "4500원"))
            dateList.add(DateData(R.drawable.ic_halfselected_cafe, "카페", "아이스 아메리카노", "4500원"))
            dateList.add(DateData(R.drawable.ic_halfselected_cafe, "카페", "아이스 아메리카노", "4500원"))
            dateList.add(DateData(R.drawable.ic_halfselected_cafe, "카페", "아이스 아메리카노", "4500원"))
        }

        //날짜 설정 부분
        viewBinding.month.text = "1" //월
        viewBinding.date.text = "1"

        //저축 금액 부분
        viewBinding.money.text = "4000"
    }
}