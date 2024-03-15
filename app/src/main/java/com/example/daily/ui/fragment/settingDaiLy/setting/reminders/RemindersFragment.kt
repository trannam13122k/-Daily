package com.example.daily.ui.fragment.settingDaiLy.setting.reminders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentRemindersBinding

class RemindersFragment : BaseFragment<FragmentRemindersBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRemindersBinding {
        return FragmentRemindersBinding.inflate(inflater)
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