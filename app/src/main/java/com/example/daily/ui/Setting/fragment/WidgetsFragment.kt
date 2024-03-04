package com.example.daily.ui.Setting.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentWidgetsBinding
import com.example.notisave.base.BaseFragment

class WidgetsFragment :BaseFragment<FragmentWidgetsBinding>(){
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWidgetsBinding {
        return FragmentWidgetsBinding.inflate(inflater)
    }


    override fun init() {

    }

    override fun setUpView() {
        setUpListener()
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
    }
}