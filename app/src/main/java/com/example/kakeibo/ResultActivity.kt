package com.example.kakeibo

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.kakeibo.databinding.ActivityResultBinding
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.TextTemplate
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class ResultActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        //progressbar 설정 및 퍼센테이지 설정
        viewBinding.progressbar.progress = 30
        viewBinding.percent.text = viewBinding.progressbar.progress.toString() + "%"

        //주차 설정
        val week = "3"
        viewBinding.week.text = week + "주차"

        //확인버튼 이벤트
        viewBinding.button.setOnClickListener {
            finish() //이전 화면으로 돌아가기
        }

        //kakao sdk 초기화
        KakaoSdk.init(this, "75cc06b6dc6baeabccc2474d39512e5d")

        //key hash 얻기 - kakao
        var keyHash = Utility.getKeyHash(this)
        Log.d("KeyHash : ", keyHash)

        //이미지 저장 버튼 이벤트
        viewBinding.image.setOnClickListener {
            //이미지 캡쳐 해서 만들기
            //post를 해야 검은 화면이 안 뜬다.
            //->뷰가  Attach 되지 않았다면 될 때까지 runnable을 연기시켜 줍니다
            viewBinding.baseLayout.post {
                val captureImage = getScreenShotFromView(viewBinding.baseLayout)

                val filename = "${System.currentTimeMillis()}.jpg"

                //이미지 저장
                if (captureImage!=null){
                    saveMediaToStorage(captureImage, filename)
                }
            }
        }

        //소셜 공유
        //인스타
        viewBinding.insta.setOnClickListener {
            //인스타 스토리 공유를 위해 이미지 먼저 저장
            val filename = "${System.currentTimeMillis()}.jpg"

            viewBinding.baseLayout.post {
                val captureImage = getScreenShotFromView(viewBinding.baseLayout)

                //이미지 저장
                if (captureImage!=null){
                    saveMediaToStorage(captureImage, filename)

                    //인스타 피드 공유
                    val type = "image/*"
                    val mediaPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path + '/' + filename
                    Log.d("path : ", mediaPath)

                    createInstagramIntent(type, mediaPath)
                }
            }

            //인스타 스토리 공유 -> 애뮬레이터 실행 안 됨
//            val intent = Intent(Intent.ACTION_SEND)
//            intent.type = "image/*"
//            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//
//            val sourceApplication = "2038752582985338"
//            intent.putExtra("source_application", sourceApplication)
//
//            val url = Uri.parse("https://ibb.co/NKZKdTL")
//
//            try {
//                intent.putExtra(Intent.EXTRA_STREAM, url)
//                intent.`package` = "com.instagram.share.ADD_TO_STORY"
//                startActivity(intent)
//            } catch (e : ActivityNotFoundException) {
//                Toast.makeText(this, "인스타그램이 없습니다", Toast.LENGTH_SHORT).show()
//            } catch (e : Exception) {
//                e.printStackTrace()
//            }
        }

        //카카오톡
        viewBinding.kakao.setOnClickListener {
            //공유할 템플릿
            val defaultFeed = TextTemplate(
                text = """
                        <거꾸로 가계부 - 저축 달성률>
                        ${week}주차 저축 보고서
                        
                        ${viewBinding.progressbar.progress}%
                        
                        "${viewBinding.ment.text}"
                        
                        -구글 스토어에서 거꾸로 가계부를 검색해주세요~!-
                        """.trimIndent(),
                link = Link(
                    webUrl = "https://developers.kakao.com",
                    mobileWebUrl = "https://developers.kakao.com"
                )
            )

            // 카카오톡 설치여부 확인
            if (ShareClient.instance.isKakaoTalkSharingAvailable(this)) {
                // 카카오톡으로 카카오톡 공유 가능
                ShareClient.instance.shareDefault(this, defaultFeed) { sharingResult, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡 공유 실패", error)
                    } else if (sharingResult != null) {
                        Log.d(TAG, "카카오톡 공유 성공 ${sharingResult.intent}")
                        startActivity(sharingResult.intent)

                        // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                        Log.w(TAG, "Warning Msg: ${sharingResult.warningMsg}")
                        Log.w(TAG, "Argument Msg: ${sharingResult.argumentMsg}")
                    }
                }
            } else {
                // 카카오톡 미설치: 웹 공유 사용 권장
                // 웹 공유 예시 코드
                val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)

                // CustomTabs으로 웹 브라우저 열기

                // 1. CustomTabsServiceConnection 지원 브라우저 열기
                // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
                try {
                    KakaoCustomTabsClient.openWithDefault(this, sharerUrl)
                } catch (e: UnsupportedOperationException) {
                    // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
                    Toast.makeText(this, "지원 브라우저가 없습니다.", Toast.LENGTH_SHORT).show()
                }

                // 2. CustomTabsServiceConnection 미지원 브라우저 열기
                // ex) 다음, 네이버 등
                try {
                    KakaoCustomTabsClient.open(this, sharerUrl)
                } catch (e: ActivityNotFoundException) {
                    // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
                    Toast.makeText(this, "인터넷 브라우저가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //트위터
        //트위터는 사진 공유를 지원하지 않음.
        viewBinding.twitter.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.setPackage("com.twitter.android")
            val content = """
                 <거꾸로 가계부 - 저축 달성률>
                 ${week}주차 저축 보고서
                        
                 ${viewBinding.progressbar.progress}%
                        
                 "${viewBinding.ment.text}"
                        
                 -구글 스토어에서 거꾸로 가계부를 검색해주세요~!-
            """.trimIndent()
            intent.putExtra(Intent.EXTRA_TEXT, content)

            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "트위터 앱이 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //view를 캡쳐하는 함수
    private fun getScreenShotFromView(v: View): Bitmap? {
        // create a bitmap object
        var screenshot: Bitmap? = null
        try {
            // inflate screenshot object
            // with Bitmap.createBitmap it
            // requires three parameters
            // width and height of the view and
            // the background color
            Log.d("measuredWidth", "w : " + v.measuredWidth + "h : " + v.measuredHeight)
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            // Now draw this bitmap on a canvas
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        // return the bitmap
        return screenshot
    }

    //이미지를 저장하는 함수
    private fun saveMediaToStorage(bitmap: Bitmap, filename : String) {
        // Generating a file name
//        val filename = "${System.currentTimeMillis()}.jpg"

        // Output stream
        var fos: OutputStream? = null

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // getting the contentResolver
            this.contentResolver?.also { resolver ->

                // Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    // putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                // Inserting the contentValues to
                // contentResolver and getting the Uri
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Captured View and saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }

    private fun createInstagramIntent(type: String, mediaPath: String) {

        // Create the new Intent using the 'Send' action.
        val share = Intent(Intent.ACTION_SEND)

        // Set the MIME type
        share.type = type

        //intent 권한 설정
        share.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        // Create the URI from the media
        val media = File(mediaPath)
//        val uri = Uri.fromFile(media)
        val uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, media)

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri)

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"))
    }
}