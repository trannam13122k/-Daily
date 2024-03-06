package com.example.daily.ui.fragment.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentCategoriesBinding

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoriesBinding {
        return FragmentCategoriesBinding.inflate(inflater)
        }

    override fun init() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun setUpView() {

    }

}