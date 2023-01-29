package com.example.kakeibo

import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.databinding.ActivityPastRecordBinding

class PastRecordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPastRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPastRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datalist: ArrayList<Past_Data> = arrayListOf()
        val PastDataAdapter = PastDataAdapter(datalist)

        binding.recyclerPastRecord.adapter = PastDataAdapter
        binding.recyclerPastRecord.layoutManager = LinearLayoutManager(this)

        datalist.apply {
            datalist.add(Past_Data(50, "50%", "2022.06.01 ~ 2023.02.01", "100만원", "티끌모아 태산"))
            datalist.add(Past_Data(25, "25%", "2022.07.01 ~ 2023.05.01", "50만원", "아직 부족하다!"))
            datalist.add(Past_Data(40, "40%", "2023.01.01 ~ 2023.07.01", "18만원", "아자아자"))
            datalist.add(Past_Data(90, "90%", "2022.04.01 ~ 2023.03.01", "1000만원", "거의 다 왔다!"))
            datalist.add(Past_Data(75, "75%", "2022.02.01 ~ 2023.01.01", "250만원", "아깝다..."))
        }

        val verticalItemDecorator = PastRecordActivity.VerticalDecorator(16)
        binding.recyclerPastRecord.addItemDecoration(verticalItemDecorator)

            binding.pastrecordExit.setOnClickListener{
                finish()
            }

        }

    class VerticalDecorator(var divHeight: Int): RecyclerView.ItemDecoration() {
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