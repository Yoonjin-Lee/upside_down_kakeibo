package com.example.kakeibo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kakeibo.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var adapter: DataItemAdapter
    var dataList: ArrayList<Data_item> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)

        initRecyclerview()

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
}