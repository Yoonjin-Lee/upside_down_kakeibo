package com.example.kakeibo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.databinding.ItemIncomeListBinding
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.*

class IncomeNewAdapter (var items: ArrayList<IncomeNoteList>) :
    RecyclerView.Adapter<IncomeNewAdapter.Holder>() {

    inner class Holder(val binding: ItemIncomeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind (data : IncomeNoteList){
            binding.imgSpendListIcn.setImageResource(data.itemIcn)
            binding.tvSpendListContent.text = data.itemContent
            binding.tvSpendListMoney.text = data.itemMoney
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            ItemIncomeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}