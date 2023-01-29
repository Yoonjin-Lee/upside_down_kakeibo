package com.example.kakeibo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.databinding.HistoryWeekListBinding

class WeekRAdapter(private val weekDataList : ArrayList<WeekData>) : RecyclerView.Adapter<WeekRAdapter.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var dateClickListener: OnDateClickListener
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
            //공유 버튼 리스너
            viewBinding.shareBtn.setOnClickListener {
                itemClickListener.onClick(it, position)
            }
            //날짜 버튼 리스너
            viewBinding.btn1.setOnClickListener {
                dateClickListener.onClick(it, position)
            }
            viewBinding.btn2.setOnClickListener {
                dateClickListener.onClick(it, position)
            }
            viewBinding.btn3.setOnClickListener {
                dateClickListener.onClick(it, position)
            }
            viewBinding.btn4.setOnClickListener {
                dateClickListener.onClick(it, position)
            }
            viewBinding.btn5.setOnClickListener {
                dateClickListener.onClick(it, position)
            }
            viewBinding.btn6.setOnClickListener {
                dateClickListener.onClick(it, position)
            }
            viewBinding.btn7.setOnClickListener {
                dateClickListener.onClick(it, position)
            }
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

    //공유 버튼
    interface OnItemClickListener{
        fun onClick(v : View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    //날짜 버튼
    interface OnDateClickListener{
        fun onClick(v : View, position: Int)
    }

    fun setDateClickListener(onDateClickListener: OnDateClickListener){
        this.dateClickListener = onDateClickListener
    }
}