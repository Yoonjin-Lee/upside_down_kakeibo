package com.example.kakeibo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.kakeibo.R
import com.example.kakeibo.databinding.ActivityIncomeBinding
import com.example.kakeibo.databinding.ActivityMainBinding

class IncomeActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityIncomeBinding
    lateinit var much : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewBinding = ActivityIncomeBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        //버튼 활성화
        //ed_much 까지 작성시 btn 활성화

        //텍스트 담을 변수
        var much : String = ""

        //버튼 비활성화
        viewBinding.startBtn.isEnabled = false

        //EditText 에 값이 있을 때만 버튼 활성화
        viewBinding.edMuch.addTextChangedListener { object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            //값 변경시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력 값 담기
                much = viewBinding.edMuch.text.toString()

                //값 유무에 따른 활성화 여부
                viewBinding.startBtn.isEnabled = much.isNotEmpty() //있다면 t 없다면 f
            }

            override fun afterTextChanged(p0: Editable?) {            }
        } }

        //버튼 이벤트
        viewBinding.startBtn.setOnClickListener{
            Toast.makeText(this, much, Toast.LENGTH_SHORT).show()
        }

    }
}
