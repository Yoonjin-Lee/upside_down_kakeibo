package com.example.kakeibo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kakeibo.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)

        viewBinding.btnHomeAdd.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.fragment_add_history_main, null)

            //해당 BottomSheetDialog에 layout 설정
            dialog.setContentView(view)

            // BottomSheetDialog 외부 화면(회색) 터치 시 종료 여부 boolean(false : 종료 안 함, true : 종료함)
            dialog.setCanceledOnTouchOutside(true)

            dialog.show()
        }
        return viewBinding.root
  }
}