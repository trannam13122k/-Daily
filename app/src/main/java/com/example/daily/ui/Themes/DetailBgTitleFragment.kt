package com.example.daily.ui.Themes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.daily.databinding.FragmentDetailBgBinding
import com.example.notisave.base.BaseFragment

class DetailBgTitleFragment :BaseFragment<FragmentDetailBgBinding>() {
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