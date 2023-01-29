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
        setContentView(binding.root)

        val datalist: ArrayList<Past_Data> = arrayListOf()
        val PastDataAdapter = PastDataAdapter(datalist)

        binding.recyclerPastRecord.adapter = PastDataAdapter
        binding.recyclerPastRecord.layoutManager = LinearLayoutManager(this)

        datalist.apply {
            datalist.add(Past_Data(50, "50%", "2022.06.01 ~ 2023.02.01", "100만원", "티끌모아 태산"))
            datalist.add(Past_Data(25, "25%", "2022.07.01 ~ 2023.05.01", "50만원", "아직 부족하다!"))

        }
            binding.pastrecordExit.setOnClickListener{
                finish()
            }
        }
    }