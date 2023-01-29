package com.example.kakeibo

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kakeibo.databinding.ItemPastRecordBinding

class PastDataAdapter(private var datalist: ArrayList<Past_Data>) : RecyclerView.Adapter<PastDataAdapter.DataViewHolder>()  {
    inner class DataViewHolder (private var viewbinding: ItemPastRecordBinding) : RecyclerView.ViewHolder(viewbinding.root) {
        fun bind(data: Past_Data) {
            viewbinding.recordProgressbar.progress = data.progressBar
            viewbinding.progressText.text = data.progressText
            viewbinding.pastPeriod.text = data.period
            viewbinding.pastPrice.text = data.price
            viewbinding.pastMemo.text = data.memo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewbinding = ItemPastRecordBinding.inflate(LayoutInflater.from(parent.context))
        return DataViewHolder(viewbinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(datalist[position])
    }

    override fun getItemCount(): Int = datalist.size
}