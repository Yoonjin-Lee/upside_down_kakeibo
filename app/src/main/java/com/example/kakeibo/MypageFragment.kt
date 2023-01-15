package com.example.kakeibo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kakeibo.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentMypageBinding.inflate(layoutInflater)

        var mypage_name = binding.mypageName
        mypage_name.append(" 님, 잘 하고 있어요!")

        var progressbar = binding.mypageProgressbar
        progressbar.max = 100
        progressbar.progress = 85

        return FragmentMypageBinding.inflate(layoutInflater).root
    }
}