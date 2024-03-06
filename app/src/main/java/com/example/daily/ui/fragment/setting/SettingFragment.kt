package com.example.daily.ui.fragment.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater)
    }

    override fun init() {

    }

    override fun setUpView() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }


}