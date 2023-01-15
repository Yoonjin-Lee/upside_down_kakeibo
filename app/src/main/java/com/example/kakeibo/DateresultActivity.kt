package com.example.kakeibo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kakeibo.databinding.ActivityDateresultBinding

class DateresultActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityDateresultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityDateresultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.backBtn.setOnClickListener {
            finish()
        }

        viewBinding.button.setOnClickListener {
            finish()
        }
    }
}