package com.example.kakeibo

import android.content.ContentValues.TAG
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

        //네이버 sdk 초기화
        val naverClientID = "bvEtHMcc0BhsXZqFdCbo"
        val naverClientSecret = "OyCdEWBPRI"
        val naverClientName = "accountbook"
        NaverIdLoginSDK.initialize(this, naverClientID, naverClientSecret, naverClientName)

        viewBinding.naverLogin.setOnClickListener {
            startNaverLogin()
        }

        viewBinding.kakaoLogin.setOnClickListener {
            NidOAuthLogin().callDeleteTokenApi(this, object : OAuthLoginCallback {
                override fun onSuccess() {
                    //서버에서 토큰 삭제에 성공한 상태입니다.
                    Log.d("naverDelete", "네이버 토큰 삭제")
                }
                override fun onFailure(httpStatus: Int, message: String) {
                    // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                    // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                    Log.d(TAG, "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                    Log.d(TAG, "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
                }
                override fun onError(errorCode: Int, message: String) {
                    // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                    // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                    onFailure(errorCode, message)
                }
            })
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
                Log.d("naver_test", "errorCode1: ${errorCode}")
                Log.d("naver_test", "errorDescription1 : ${errorDescription}")
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
                Log.d("naver_test", "naverToken : ${naverToken}")
//                var naverRefreshToken = NaverIdLoginSDK.getRefreshToken()
//                var naverExpiresAt = NaverIdLoginSDK.getExpiresAt().toString()
//                var naverTokenType = NaverIdLoginSDK.getTokenType()
//                var naverState = NaverIdLoginSDK.getState().toString()

                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)

                //페이지 넘어가기
                val intent = Intent(this@LoginActivity, GoalActivity::class.java)
                startActivity(intent)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
//                Toast.makeText(this, "errorCode: ${errorCode}\n" + "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
                Log.d("naver_test", "errorCode2: ${errorCode}")
                Log.d("naver_test", "errorDescription2 : ${errorDescription}")
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

}