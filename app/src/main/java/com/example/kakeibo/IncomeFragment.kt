package com.example.kakeibo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kakeibo.databinding.ActivityIncomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class IncomeFragment : Fragment() {
    private lateinit var viewBinding: ActivityIncomeBinding
    private lateinit var adapter: IncomeNewAdapter
    var dataList: ArrayList<IncomeNoteList> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = ActivityIncomeBinding.inflate(inflater, container, false)

        initRecyclerview()

        val bottomSheetDialogFragment = AddSpendingIncomeFragment()
        //모서리 둥글게
        bottomSheetDialogFragment.setStyle(
            BottomSheetDialogFragment.STYLE_NORMAL,
            R.style.RoundCornerBottomSheetDialogTheme
        )

        //내역 추가
        viewBinding.newNoteButtonMain.setOnClickListener {
            bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)
        }

        //다음 버튼
        viewBinding.startBtn.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        return viewBinding.root
    }

    fun initAddData() {
        val icn = arguments?.getInt("dataIcn") //아이콘
        val content = arguments?.getString("dataContent") //내용
        val money = arguments?.getString("dataMoney") //금액 입력

        dataList.add(
            IncomeNoteList(icn!!, content.toString(), money.toString())
        )

        viewBinding.recyclerViewMain.adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerview() {
        viewBinding.recyclerViewMain.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        //adapter 생성
        adapter = IncomeNewAdapter(dataList)
        viewBinding.recyclerViewMain.adapter = adapter
    }
}