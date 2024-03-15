package com.example.daily.ui.fragment.settingDaiLy.affirmations.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentFavouriteBinding

class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>(){
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavouriteBinding {
        return FragmentFavouriteBinding.inflate(inflater)
    }

    override fun init() {

    }

    override fun setUpView() {
        listenerClick()
    }
    private fun listenerClick() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}