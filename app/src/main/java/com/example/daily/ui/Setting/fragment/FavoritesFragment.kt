package com.example.daily.ui.Setting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentFavoritesBinding
import com.example.notisave.base.BaseFragment

class FavoritesFragment :BaseFragment<FragmentFavoritesBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater)
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