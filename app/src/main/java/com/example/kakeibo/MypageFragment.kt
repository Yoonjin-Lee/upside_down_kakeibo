package com.example.kakeibo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kakeibo.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(layoutInflater)

        var mypage_name = binding.mypageName
        mypage_name.append("님, 잘 하고 있어요!")

        binding.mypageProgressbar.progress = 30
       binding.mypagePercent.text = binding.mypageProgressbar.progress.toString() + "%"


        binding.mypageExit.setOnClickListener{
            val intent = Intent(context, HomeFragment::class.java);
            startActivity(intent);
        }
        binding.mypageReset.setOnClickListener {
            val intent = Intent(context, mypage_dialog::class.java);
            startActivity(intent);
        }
        binding.mypagePastrecord.setOnClickListener {
            val intent = Intent(context, PastRecordActivity::class.java);
            startActivity(intent);
        }


        return binding.root
    }

}