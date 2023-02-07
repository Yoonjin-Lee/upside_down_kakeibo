package com.example.kakeibo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
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

            // 버튼 색 설정
            if (data.mon_check == 0) {
                viewBinding.btn1.setBackgroundResource(R.drawable.day_fail_button)
                viewBinding.btn1.setTextColor(Color.rgb(181, 0, 0))
            } else if (data.mon_check == 3){
                viewBinding.btn1.setBackgroundResource(R.drawable.day_null_button)
                viewBinding.btn1.setTextColor(Color.rgb(217,217,217))
            }
            if (data.tue_check == 0) {
                viewBinding.btn2.setBackgroundResource(R.drawable.day_fail_button)
                viewBinding.btn2.setTextColor(Color.rgb(181, 0, 0))
            } else if (data.tue_check == 3){
                viewBinding.btn2.setBackgroundResource(R.drawable.day_null_button)
                viewBinding.btn2.setTextColor(Color.rgb(217,217,217))
            }
            if (data.wed_check == 0) {
                viewBinding.btn3.setBackgroundResource(R.drawable.day_fail_button)
                viewBinding.btn3.setTextColor(Color.rgb(181, 0, 0))
            } else if (data.wed_check == 3){
                viewBinding.btn3.setBackgroundResource(R.drawable.day_null_button)
                viewBinding.btn3.setTextColor(Color.rgb(217,217,217))
            }
            if (data.thu_check == 0) {
                viewBinding.btn4.setBackgroundResource(R.drawable.day_fail_button)
                viewBinding.btn4.setTextColor(Color.rgb(181, 0, 0))
            } else if (data.thu_check == 3){
                viewBinding.btn4.setBackgroundResource(R.drawable.day_null_button)
                viewBinding.btn4.setTextColor(Color.rgb(217,217,217))
            }
            if (data.fri_check == 0) {
                viewBinding.btn5.setBackgroundResource(R.drawable.day_fail_button)
                viewBinding.btn5.setTextColor(Color.rgb(181, 0, 0))
            } else if (data.fri_check == 3){
                viewBinding.btn5.setBackgroundResource(R.drawable.day_null_button)
                viewBinding.btn5.setTextColor(Color.rgb(217,217,217))
            }
            if (data.sat_check == 0) {
                viewBinding.btn6.setBackgroundResource(R.drawable.day_fail_button)
                viewBinding.btn6.setTextColor(Color.rgb(181, 0, 0))
            } else if (data.sat_check == 3){
                viewBinding.btn6.setBackgroundResource(R.drawable.day_null_button)
                viewBinding.btn6.setTextColor(Color.rgb(217,217,217))
            }
            if (data.sun_check == 0) {
                viewBinding.btn7.setBackgroundResource(R.drawable.day_fail_button)
                viewBinding.btn7.setTextColor(Color.rgb(181, 0, 0))
            } else if (data.sun_check == 3){
                viewBinding.btn7.setBackgroundResource(R.drawable.day_null_button)
                viewBinding.btn7.setTextColor(Color.rgb(217,217,217))
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