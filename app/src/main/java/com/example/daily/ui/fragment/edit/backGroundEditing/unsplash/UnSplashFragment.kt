package com.example.daily.ui.fragment.edit.backGroundEditing.unsplash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentUnSplashBinding

class UnSplashFragment : BaseFragment<FragmentUnSplashBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUnSplashBinding {
        return FragmentUnSplashBinding.inflate(inflater)
    }

    override fun init() {
    clickButton()
    }

    private fun clickButton() {
        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun setUpView() {

    }


}