package com.example.daily.ui.fragment.themes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentThemesBinding

class ThemesFragment : BaseFragment<FragmentThemesBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentThemesBinding {
        return FragmentThemesBinding.inflate(inflater)
    }

    override fun init() {

    }

    override fun setUpView() {
        ClickListener()
    }

    private fun ClickListener() {
        binding.ivBack.setOnClickListener {
           activity?.onBackPressed()
        }
    }

}