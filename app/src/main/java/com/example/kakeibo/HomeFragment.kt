package com.example.kakeibo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kakeibo.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var adapter: DataItemAdapter
    var dataList: ArrayList<Data_item> = arrayListOf()
    val fixedTodayAvailable: Int = 100000 //오늘 소비 가능 금액 세팅(초기 금액)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)

        //init 세팅
        initRecyclerview() //리사이클러뷰 기본 세팅
        viewBinding.tvIntTodayAvailable.text =
            fixedTodayAvailable.toString() //'오늘 소비 가능 금액' = '초기 금액'으로 세팅
        setProgressBarHeight(0) //프로그레스 바 높이 0으로 세팅

        val bottomSheetDialogFragment = AddHistoryMainFragment()
        //모서리 둥글게
        bottomSheetDialogFragment.setStyle(
            BottomSheetDialogFragment.STYLE_NORMAL,
            R.style.RoundCornerBottomSheetDialogTheme
        )

        //btnHomeAdd('+' 버튼) 눌렀을 때 dialog 뜨도록 구현
        viewBinding.btnHomeAdd.setOnClickListener {
            bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)
        }

        return viewBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initAddData() {
        val icn = arguments?.getInt("dataIcn") //아이콘
        val content = arguments?.getString("dataContent") //내용
        val money = arguments?.getString("dataMoney") //금액 입력
        val memo = arguments?.getString("dataMemo") //한 줄 메모

        dataList.add(
            Data_item(icn!!, content.toString(), money.toString(), memo.toString())
        )

        viewBinding.recyclerSpendList.adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerview() {
        viewBinding.recyclerSpendList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        //adapter 생성
        adapter = DataItemAdapter(dataList)
        viewBinding.recyclerSpendList.adapter = adapter
    }

//    //'오늘 소비 가능 금액' int로 return하는 함수(DialogFragment.kt에서 사용함)
//    fun returnTodayInt(): Int {
//        return viewBinding.tvIntTodayAvailable.text.toString().toInt()
//    }

    //'오늘 소비 가능 금액' 갱신해주는 함수
    fun setTodayAvailable(changedTodayAvailable: Int) {
        viewBinding.tvIntTodayAvailable.text = changedTodayAvailable.toString()
    }

    //프로그레스 바 높이를 height로 설정하는 함수
    fun setProgressBarHeight(height: Int) {
        //막대
        viewBinding.progressBarToday.progress = height

        //이미지
        val circle = viewBinding.icProgressBarCircle
//        val params = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
//        params.setMargins(0,0,0,20+(height*3.65).toInt())
//        circle.layoutParams = params

        //이미지 높이
        val circleHeight: Float = 20 + (height * 3.65).toFloat()
        //이미지가 프로그래스바 위로 튀어나가지 않게 marginBottom의 최대값을 385로 설정함
        if (circleHeight > 385) {
            circle.margin(bottom = 385.toFloat())
        } else {
            circle.margin(bottom = circleHeight)
        }
    }

    //프로그레스 이미지 높이 설정할 때 쓰인 함수들
    fun View.margin(
        left: Float? = null,
        top: Float? = null,
        right: Float? = null,
        bottom: Float? = null
    ) {
        layoutParams<ViewGroup.MarginLayoutParams> {
            left?.run { leftMargin = dpToPx(this) }
            top?.run { topMargin = dpToPx(this) }
            right?.run { rightMargin = dpToPx(this) }
            bottom?.run { bottomMargin = dpToPx(this) }
        }
    }

    inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
        if (layoutParams is T) block(layoutParams as T)
    }

    fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
    fun Context.dpToPx(dp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    //
}