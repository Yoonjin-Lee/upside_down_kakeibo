package com.example.kakeibo

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kakeibo.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        //네이버 초기화
        val naverClientId = getString(R.string.social_login_info_naver_client_id)
        val naverClientSecret = getString(R.string.social_login_info_naver_client_secret)
        val naverClientName = getString(R.string.social_login_info_naver_client_name)
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret , naverClientName)
        setLayoutState(false)

        //카카오 초기화
        KakaoSdk.init(this, "75cc06b6dc6baeabccc2474d39512e5d")
        var keyHash = Utility.getKeyHash(this)
        Log.d("KeyHash : ", keyHash)

        viewBinding.naverLogin.setOnClickListener {
            startNaverLogin()
        }

        viewBinding.kakaoLogin.setOnClickListener {
            startKaKaoLogin()
        }
    }

    private fun startNaverLogin(){
        var naverToken :String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userId = response.profile?.id
                val userEmail = response.profile?.email.toString()
                val userNickname = response.profile?.nickname.toString()
                Log.d("login", "id: ${userId} \ntoken: ${naverToken}")
                setLayoutState(true)

                Toast.makeText(this@LoginActivity, "네이버 아이디 로그인 성공!", Toast.LENGTH_SHORT).show()

                //서버로 데이터 보내기
                val authService = getRetrofit().create(ApiService::class.java)
                authService.loginNaver(userEmail, userNickname).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            if (data != null) {
                                Log.d("test_retrofit", "네이버 로그인 정보 :" + data)
                                if (data == "no goal") {
                                    // 목표 설정 없음
                                    val intent = Intent(this@LoginActivity, GoalActivity::class.java)
                                    startActivity(intent)
                                } else if(data == "exist"){
                                    //목표 설정 있음
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        } else {
                            Log.w("retrofit", "실패 ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.w("retrofit", "네이버 로그인 정보 접근 실패", t)
                        Log.w("retrofit", "네이버 로그인 정보 접근 실패 response",)
                    }
                })
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@LoginActivity, "errorCode: ${errorCode}\n" +
                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@LoginActivity, "errorCode: ${errorCode}\n" +
                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

    /**
     * 로그아웃
     * 애플리케이션에서 로그아웃할 때는 다음과 같이 NaverIdLoginSDK.logout() 메서드를 호출합니다. */
    private fun startNaverLogout(){
        NaverIdLoginSDK.logout()
        setLayoutState(false)
        Toast.makeText(this@LoginActivity, "네이버 아이디 로그아웃 성공!", Toast.LENGTH_SHORT).show()
    }

    /**
     * 연동해제
     * 네이버 아이디와 애플리케이션의 연동을 해제하는 기능은 다음과 같이 NidOAuthLogin().callDeleteTokenApi() 메서드로 구현합니다.
    연동을 해제하면 클라이언트에 저장된 토큰과 서버에 저장된 토큰이 모두 삭제됩니다.
     */
    private fun startNaverDeleteToken(){
        NidOAuthLogin().callDeleteTokenApi(this, object : OAuthLoginCallback {
            override fun onSuccess() {
                //서버에서 토큰 삭제에 성공한 상태입니다.
                setLayoutState(false)
                Toast.makeText(this@LoginActivity, "네이버 아이디 토큰삭제 성공!", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(httpStatus: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                Log.d("naver", "errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Log.d("naver", "errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }
            override fun onError(errorCode: Int, message: String) {
                // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                onFailure(errorCode, message)
            }
        })
    }

    private fun setLayoutState(login: Boolean){
        if(login){
//            val intent = Intent(applicationContext, GoalActivity::class.java)
//            startActivity(intent)
        }else{
//            viewBinding.tvNaverLogin.visibility = View.VISIBLE
//            viewBinding.tvNaverLogout.visibility = View.GONE
//            viewBinding.tvNaverDeleteToken.visibility = View.GONE
//            viewBinding.tvResult.text = ""
        }
    }

    private fun startKaKaoLogin() {
        // 로그인 조합 예제

        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")

                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    }
                    else if (user != null) {
                        Log.i(TAG, "사용자 정보 요청 성공" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}")

                        val userEmail = user.kakaoAccount?.email
                        val userNickname = user.kakaoAccount?.profile?.nickname

                        //서버로 데이터 보내기
                        val authService = getRetrofit().create(ApiService::class.java)
                        authService.loginNaver(userEmail, userNickname).enqueue(object : Callback<String> {
                            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                                if (response.isSuccessful) {
                                    val data = response.body()
                                    if (data != null) {
                                        Log.d("test_retrofit", "카카오계정 로그인 정보 :" + data)
                                        if (data == "no goal") {
                                            // 목표 설정 없음
                                            val intent = Intent(this@LoginActivity, GoalActivity::class.java)
                                            startActivity(intent)
                                        } else if(data == "exist"){
                                            //목표 설정 있음
                                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        }
                                    }
                                } else {
                                    Log.w("retrofit", "실패 ${response.code()}")
                                }
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                Log.w("retrofit", "카카오계정 로그인 정보 접근 실패", t)
                                Log.w("retrofit", "카카오계정 로그인 정보 접근 실패 response",)
                            }
                        })
                    }
                }
            }
        }

         // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e(TAG, "사용자 정보 요청 실패", error)
                        }
                        else if (user != null) {
                            Log.i(TAG, "사용자 정보 요청 성공" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}")

                            // 이메일, 닉네임 가져오기
                            val userEmail = user.kakaoAccount?.email
                            val userNickname = user.kakaoAccount?.profile?.nickname

                            //서버로 데이터 보내기
                            val authService = getRetrofit().create(ApiService::class.java)
                            authService.loginNaver(userEmail, userNickname).enqueue(object : Callback<String> {
                                override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                                    if (response.isSuccessful) {
                                        val data = response.body()
                                        if (data != null) {
                                            Log.d("test_retrofit", "카카오톡 로그인 정보 :" + data)
                                            if (data == "no goal") {
                                                // 목표 설정 없음
                                                val intent = Intent(this@LoginActivity, GoalActivity::class.java)
                                                startActivity(intent)
                                            } else if(data == "exist"){
                                                //목표 설정 있음
                                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                            }
                                        }
                                    } else {
                                        Log.w("retrofit", "실패 ${response.code()}")
                                    }
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.w("retrofit", "카카오톡 로그인 정보 접근 실패", t)
                                    Log.w("retrofit", "카카오톡 로그인 정보 접근 실패 response",)
                                }
                            })
                        }
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }
}
