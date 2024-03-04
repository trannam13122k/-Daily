package com.example.daily.ui.Themes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.daily.databinding.FragmentDetailBgBinding
import com.example.notisave.base.BaseFragment

class DetailBackGroundFragment :BaseFragment<FragmentDetailBgBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBgBinding {
        return FragmentDetailBgBinding.inflate(inflater)
    }

    override fun init() {
    }

    override fun setUpView() {

    }

}