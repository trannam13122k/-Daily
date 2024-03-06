package com.example.daily.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentContentBinding
import com.example.daily.model.Content
import com.example.daily.ui.fragment.mainFragment.MainContentAdapter

class ContentFragment() : BaseFragment<FragmentContentBinding>() {

    private var adapter: MainContentAdapter? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentContentBinding {
     return FragmentContentBinding.inflate(inflater)
    }

    override fun init() {
         val bundleReceive = arguments
        if(bundleReceive != null){
            val content : Content = bundleReceive.get("fragment")
            if(content != null){
                binding.tvContent.setText(content.)
            }
        }
    }

    override fun setUpView() {

    }


}