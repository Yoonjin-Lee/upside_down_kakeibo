package com.example.kakeibo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.databinding.ItemSpendListBinding
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.*

class DataItemAdapter(var items: ArrayList<Data_item>) :
    RecyclerView.Adapter<DataItemAdapter.Holder>() {

    inner class Holder(val binding: ItemSpendListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
//                itemClickListener?.OnItemClick(items[adapterPosition]) //?는 null일 수 도 있다고 알려주는 역할
//                Log.d("touch3", adapterPosition.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            ItemSpendListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            imgSpendListIcn.setImageResource(items[position].itemIcn) //아이콘
            tvSpendListContent.text = items[position].itemContent //내용
            tvSpendListMoney.text = items[position].itemMoney //금액 입력
            tvSpendListMemo.text = items[position].itemMemo //한 줄 메모
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}