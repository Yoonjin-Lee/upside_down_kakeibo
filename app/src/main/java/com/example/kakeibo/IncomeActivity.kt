package com.example.kakeibo
import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
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


        //slide
        val lv = findViewById<ListView>(R.id.add_btn);0
        lv.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            arrayOf("SlideUp Dialog 1")
        )
        lv.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
            if (position == 0) {
                onSlideUpDialog();
            }
        }

        
        
        //btn 활성화 부분 코드 시작
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


    private fun onSlideUpDialog() {
        var contentView: View = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.fragment_add_history_main, null)
        val slideupPopup = SlideUpDialog.Builder(this)
            .setContentView(contentView)
            .create()
        slideupPopup.show()
        contentView.findViewById<Button>(R.id.btn_add_history_main_done).setOnClickListener {
            slideupPopup.dismissAnim()
        }
    }
}
