package com.example.kakeibo

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.databinding.HistoryWeekListBinding

class WeekRAdapter(private val weekDataList : ArrayList<WeekData>) : RecyclerView.Adapter<WeekRAdapter.ViewHolder>() {
    inner class ViewHolder(private val viewBinding: HistoryWeekListBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(data: WeekData){
            viewBinding.week.text = data.week
            viewBinding.btn1.text = data.mon
            viewBinding.btn2.text = data.tue
            viewBinding.btn3.text = data.wed
            viewBinding.btn4.text = data.thu
            viewBinding.btn5.text = data.fri
            viewBinding.btn6.text = data.sat
            viewBinding.btn7.text = data.sun
            viewBinding.money.text = data.money
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = HistoryWeekListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(weekDataList[position])
    }

    override fun getItemCount(): Int = weekDataList.size
}