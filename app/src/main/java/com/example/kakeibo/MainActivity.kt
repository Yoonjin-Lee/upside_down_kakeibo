package com.example.kakeibo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kakeibo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.fragment.id, HomeFragment()) //homefragment
            .commitAllowingStateLoss()

        viewBinding.bnb.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.fragment.id, HomeFragment()) //homefragment
                            .commitAllowingStateLoss()
                    }
                    R.id.history -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.fragment.id, HistoryFragment()) //historyfragment
                            .commitAllowingStateLoss()
                    }
                    R.id.mypage -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.fragment.id, Mypage1()) //mypagefragment
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.home
        }
    }
}