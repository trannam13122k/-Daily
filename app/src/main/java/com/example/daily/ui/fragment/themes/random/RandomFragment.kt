package com.example.daily.ui.fragment.themes.random

import android.util.Log
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
        val randomImage = arguments?.getString("randomImage")
        Log.d("RandomFragment", "Received randomImage: $randomImage")

    }

    override fun setUpView() {

    }

}