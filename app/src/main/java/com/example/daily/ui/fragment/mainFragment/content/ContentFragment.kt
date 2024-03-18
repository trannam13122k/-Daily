package com.example.daily.ui.fragment.mainFragment.content

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentContentBinding

class ContentFragment() : BaseFragment<FragmentContentBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentContentBinding {
     return FragmentContentBinding.inflate(inflater)
    }

    override fun init() {
        val bundleN = arguments
        if (bundleN != null) {
            val content: Content? = bundleN["question_object"] as Content?
            if (content != null) {
                binding.tvContent.setText( content.name)
            }
        }

        binding.imgFavourite.setOnClickListener {
            Toast.makeText(requireContext(),"Favourite ",Toast.LENGTH_SHORT).show()
        }
    }

    override fun setUpView() {



    }


}