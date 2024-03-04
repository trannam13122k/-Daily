package com.example.daily.ui.Setting.add


import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentAddBinding
import com.example.notisave.base.BaseFragment

class AddFragment :BaseFragment<FragmentAddBinding>(){
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddBinding {
        return FragmentAddBinding.inflate(inflater)
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