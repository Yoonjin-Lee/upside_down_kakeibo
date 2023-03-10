package com.example.kakeibo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kakeibo.databinding.ActivityMypageLogoutBinding
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class Mypage_logout : AppCompatActivity() {
    private lateinit var binding: ActivityMypageLogoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutNo.setOnClickListener {
            finish() //이전 화면으로 돌아가기
        }
        binding.logoutYes.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            //로그아웃 구현
            LoginActivity().startNaverDeleteToken()
            LoginActivity().startNaverLogout()


            startActivity(intent);
            finish()
        }
    }
}