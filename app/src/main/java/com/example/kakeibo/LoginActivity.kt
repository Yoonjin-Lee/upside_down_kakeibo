package com.example.kakeibo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kakeibo.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.naverLogin.setOnClickListener {
//            val authService = getRetrofit().create(ApiService::class.java)
//
//            authService.getHistoryData().enqueue(object : Callback<List<ServerHistoryData>> {
//                override fun onResponse(
//                    call: Call<List<ServerHistoryData>>,
//                    response: retrofit2.Response<List<ServerHistoryData>>
//                ) {
//                    if (response.isSuccessful) {
//                        val data = response.body()
//
//                        if (data != null) {
//                            Log.d("test_retrofit", "네이버 아이디 정보:" + data)
//
//                            //로그인 성공 시, 페이지 넘어가기
//                            val intent = Intent(this@LoginActivity, GoalActivity::class.java)
//                            startActivity(intent)
//                        }
//                    } else {
//                        Log.w("retrofit", "네이버 아이디 실패 ${response.code()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<List<ServerHistoryData>>, t: Throwable) {
//                    Log.w("retrofit", "네이버 아이디 정보 접근 실패", t)
//                    Log.w("retrofit", "네이버 아이디 정보 접근 실패 response",)
//                }
//            })

            //로그인 성공 시, 페이지 넘어가기
            val intent = Intent(this, GoalActivity::class.java)
            startActivity(intent)
        }
    }
}