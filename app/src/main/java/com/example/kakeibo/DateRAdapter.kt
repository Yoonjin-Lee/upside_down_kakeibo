package com.example.kakeibo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.databinding.DateSpendListBinding

class DateRAdapter(private val dateList : ArrayList<DateData>) : RecyclerView.Adapter<DateRAdapter.ViewHolder>(){
    inner class ViewHolder(val viewBinding : DateSpendListBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data: DateData){
            viewBinding.imgSpendListIcn.setImageResource(data.icon)
            viewBinding.tvSpendListCategory.text = data.content
            viewBinding.tvSpendListContent.text = data.memo
            viewBinding.tvSpendListMoney.text = data.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = DateSpendListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dateList[position])
    }

    override fun getItemCount(): Int = dateList.size
}