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

        // intent로 받아와야 할 부분 : historyId, 날짜, 저축 가능 금액
        var historyId = 0

        if (intent.hasExtra("historyId")){
            historyId = intent.getIntExtra("historyID", 0)
        }
        if (intent.hasExtra("date")){
            // 날짜 설정
//            viewBinding.month.text = intent.getStringExtra("date")!!.split('.')[2]
//            viewBinding.date.text = intent.getStringExtra("date")!!.split('.')[3]
            viewBinding.month.text = "3"
            viewBinding.date.text = "3"
        }
        if (intent.hasExtra("money")){
            // 저축 가능 금액 설정
//            viewBinding.money.text = intent.getStringExtra("money")
            viewBinding.money.text = "10000"
        }

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

        authService.getExpenditures(historyId).enqueue(object : Callback<List<ServerExpenditureData>> {
            override fun onResponse(call: Call<List<ServerExpenditureData>>, response: retrofit2.Response<List<ServerExpenditureData>>) {
                if (response.isSuccessful) {
                    val data = response.body()

                    if (data != null) {
                        Log.d("test_retrofit", "특정일 소비 정보 : ${data.toString()}")

                        for(i in data){
                            adapter.apply {
                                dateList.add(DateData(i.icon.toInt(), i.content, i.memo, i.cost.toString()))
                            }
                        }

                    }
                } else {
                    Log.w("retrofit", "실패 ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ServerExpenditureData>>, t: Throwable) {
                Log.w("retrofit", "특정일 소비 정보 접근 실패", t)
            }
        })

//        adapter.apply {
//            dateList.add(DateData(R.drawable.ic_halfselected_cafe, "카페", "아이스 아메리카노", "4500원"))
//            dateList.add(DateData(R.drawable.ic_halfselected_cafe, "카페", "아이스 아메리카노", "4500원"))
//            dateList.add(DateData(R.drawable.ic_halfselected_cafe, "카페", "아이스 아메리카노", "4500원"))
//            dateList.add(DateData(R.drawable.ic_halfselected_cafe, "카페", "아이스 아메리카노", "4500원"))
//        }
    }
}