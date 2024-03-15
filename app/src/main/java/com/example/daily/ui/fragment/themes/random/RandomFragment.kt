package com.example.daily.ui.fragment.themes.random

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentRandomBinding

class RandomFragment : BaseFragment<FragmentRandomBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRandomBinding {
        return FragmentRandomBinding.inflate(inflater)
    }

    override fun init() {

    }

    override fun setUpView() {

    }

}