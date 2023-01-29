package com.example.kakeibo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kakeibo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)

        val bottomSheetDialogFragment = AddHistoryMainFragment()

        viewBinding.btnHomeAdd.setOnClickListener{
            bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)
        }

        return viewBinding.root
    }
}