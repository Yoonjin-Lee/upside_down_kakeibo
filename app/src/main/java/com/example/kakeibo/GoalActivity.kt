package com.example.umc__


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.example.kakeibo.databinding.ActivityGoalBinding
import com.example.kakeibo.databinding.ActivityMainBinding


class GoalActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityGoalBinding
    lateinit var memo : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityGoalBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        //시크바
        val binding by lazy{ ActivityMainBinding.inflate(layoutInflater) }

        //시크바 개월 수
        setContentView(binding.root)
        viewBinding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?,progress: Int,
                                           fromUser: Boolean) {
                viewBinding.month.text = "$progress"+"개월"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })


        //버튼 활성화
        //ed_memo 까지 작성시 btn 활성화

        //텍스트 담을 변수
        var memo : String = ""

        //버튼 비활성화
        viewBinding.btn.isEnabled = false

        //EditText 에 값이 있을 때만 버튼 활성화
        viewBinding.edMemo.addTextChangedListener { object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            //값 변경시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력 값 담기
                memo = viewBinding.edMemo.text.toString()

                //값 유무에 따른 활성화 여부
                viewBinding.btn.isEnabled = memo.isNotEmpty() //있다면 t 없다면 f
            }

            override fun afterTextChanged(p0: Editable?) {            }
        } }

        //버튼 이벤트
        viewBinding.btn.setOnClickListener{
            Toast.makeText(this, memo, Toast.LENGTH_SHORT).show()
        }


    }
}