package com.example.kakeibo

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private lateinit var viewBinding: FragmentHistoryBinding
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

        //테스트 데이터
        adapter.apply {
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20", "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20", "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20", "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20", "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20", "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20", "50"))
            weekDataList.add(WeekData("1주차", "6/14", "6/15", "6/16", "6/17", "6/18", "6/19", "6/20", "50"))
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