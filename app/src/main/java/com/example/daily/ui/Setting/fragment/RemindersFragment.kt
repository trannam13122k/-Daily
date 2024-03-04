package com.example.daily.ui.Setting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentRemindersBinding
import com.example.notisave.base.BaseFragment

class RemindersFragment :BaseFragment<FragmentRemindersBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRemindersBinding {
        return FragmentRemindersBinding.inflate(inflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
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