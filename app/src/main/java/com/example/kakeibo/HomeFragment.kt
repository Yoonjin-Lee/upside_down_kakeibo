package com.example.kakeibo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kakeibo.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)

        val bottomSheetDialogFragment = AddHistoryMainFragment()

        //모서리 둥글게
        bottomSheetDialogFragment.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)

        //btnHomeAdd('+' 버튼) 눌렀을 때 dialog 뜨도록 구현
        viewBinding.btnHomeAdd.setOnClickListener{
            bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)
        }
        
        return viewBinding.root
    }
}