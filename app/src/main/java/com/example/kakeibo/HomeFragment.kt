package com.example.kakeibo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kakeibo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    //확인하려고 임시로 만듦
    //비율이 안 맞음 밑에 검은색으로 만든 거 빼야 할 듯?
    private lateinit var viewBinding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)
        return viewBinding.root
    }
}