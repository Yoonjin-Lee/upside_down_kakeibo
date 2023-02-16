package com.example.kakeibo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.kakeibo.databinding.FragmentHomeExcessDialogBinding

class HomeFragmentExcessDialog : DialogFragment() {
    private lateinit var viewBinding: FragmentHomeExcessDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeExcessDialogBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

}