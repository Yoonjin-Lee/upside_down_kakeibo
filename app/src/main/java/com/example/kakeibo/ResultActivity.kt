package com.example.kakeibo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kakeibo.databinding.ActivityResultBinding
import java.text.SimpleDateFormat
import java.util.Date

class ResultActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        //progressbar 설정 및 퍼센테이지 설정
        viewBinding.progressbar.progress = 30
        viewBinding.percent.text = viewBinding.progressbar.progress.toString() + "%"

        //확인버튼 이벤트
        viewBinding.button.setOnClickListener {
            finish() //이전 화면으로 돌아가기
        }

        //이미지 저장 이름 포맷
        val sdf = SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.getDefault())
        var current_time = sdf.format(Date())

        //이미지 저장 버튼 이벤트
        viewBinding.image.setOnClickListener {

        }
    }
}