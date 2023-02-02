package com.example.kakeibo

import android.content.Intent
import android.media.session.MediaSession.Token
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kakeibo.databinding.ActivityLoginBinding
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityLoginBinding
    var naverToken :String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

//        viewBinding.naverLogin.setOnClickListener {
//            val authService = getRetrofit().create(ApiService::class.java)
//
//            authService.naverLogin().enqueue(object : Callback<String> {
//                override fun onResponse(
//                    call: Call<String>,
//                    response: retrofit2.Response<String>
//                ) {
//                    if (response.isSuccessful) {
//                        val data = response.body()
//
//                        if (data != null) {
//                            Log.d("test_retrofit", "네이버 아이디 정보:" + data)
//                            Log.d("test_retrofit", "성공")
//
//                            val internet = Intent(
//                                Intent.ACTION_VIEW,
//                                Uri.parse(data)
//                            )
//                            startActivity(internet)
//                        }
//                    } else {
//                        Log.w("retrofit", "네이버 아이디 실패 ${response.code()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    Log.w("retrofit", "네이버 아이디 정보 접근 실패", t)
//                    Log.w("retrofit", "네이버 아이디 정보 접근 실패 response",)
//                }
//            })
//        }

            //url로 이동
//            val internet = Intent(Intent.ACTION_VIEW, Uri.parse("http://ekh-be2.shop/oauth2/authorization/naver"))
//            startActivity(internet)
//
//            //로그인 성공 시, 페이지 넘어가기
//            val intent = Intent(this@LoginActivity, GoalActivity::class.java)
//            startActivity(intent)
//        }

        //네이버 sdk 초기화
        val naverClientID = "Rh5NFhz80b9ge0Qnrw6O"
        val naverClientSecret = "Wk6J6blYuZ"
        val naverClientName = "accountbook"
        NaverIdLoginSDK.initialize(this, naverClientID, naverClientSecret, naverClientName)

        viewBinding.naverLogin.setOnClickListener {
            startNaverLogin()
        }

    }

    private fun startNaverLogin(){

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userId = response.profile?.id
//                binding.tvResult.text = "id: ${userId} \ntoken: ${naverToken}"
//                setLayoutState(true)
//                Toast.makeText(this, "네이버 아이디 로그인 성공!", Toast.LENGTH_SHORT).show()
                Log.d("naver_test", "네이버 아이디 로그인 성공")
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
//                Toast.makeText(this, "errorCode: ${errorCode}\n" +
//                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
                Log.d("naver_test", "errorCode: ${errorCode}")
                Log.d("naver_test", "errorDescription : ${errorDescription}")
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        /** OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다. */
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()

                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
//                Toast.makeText(this, "errorCode: ${errorCode}\n" + "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
                Log.d("naver_test", "errorCode: ${errorCode}")
                Log.d("naver_test", "errorDescription : ${errorDescription}")
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }
}