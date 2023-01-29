package com.example.kakeibo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kakeibo.databinding.ActivityDateresultBinding

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