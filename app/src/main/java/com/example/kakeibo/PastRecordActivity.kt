package com.example.kakeibo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kakeibo.databinding.ActivityPastRecordBinding

class PastRecordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPastRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPastRecordBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_past_record)

        val datalist: ArrayList<Past_Data> = arrayListOf()
        val PastDataAdapter = PastDataAdapter(datalist)

        binding.recyclerPastRecord.adapter = PastDataAdapter
        binding.recyclerPastRecord.layoutManager = LinearLayoutManager(this)

        datalist.add(Past_Data(85, "85%", "2022.06.01 ~ 2023.02.01", "100만원", "티끌모아 태산"))

        binding.mypageExit.setOnClickListener{
            val intent = Intent(this@PastRecordActivity, MypageFragment::class.java)
            startActivity(intent)
        }
    }
}