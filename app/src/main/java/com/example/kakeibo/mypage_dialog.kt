package com.example.kakeibo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.kakeibo.databinding.ActivityMypageDialogBinding

class mypage_dialog : AppCompatActivity() {
    private lateinit var binding: ActivityMypageDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mypageNo.setOnClickListener {
            finish() //이전 화면으로 돌아가기
        }
        binding.mypageYes.setOnClickListener{
            //if (binding.mypageCheck.isChecked) {
                val intent = Intent(this, GoalActivity::class.java);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish()
            //}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}