package com.example.daily.ui.fragment.settingDaiLy.settingMain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentSettingBinding
import com.example.daily.util.DataB

class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    lateinit var settingAdapter: SettingAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater)
    }

    override fun init() {

    }

    override fun setUpView() {
        listenerClick()
        settings()
        affirmations()
    }

    private fun listenerClick() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun settings() {
        with(binding.rvSetting) {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = linearLayoutManager
            settingAdapter = SettingAdapter(DataB.listSettings)
            adapter = settingAdapter
        }

        settingAdapter.onClickItem = { setting ->
            if (setting.fragment != null) {
                openFragment(setting.fragment, null, true)
            }
        }
    }

    private fun affirmations() {
        with(binding.rvAffirmations) {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = linearLayoutManager
            settingAdapter = SettingAdapter(DataB.listAffirmations)
            adapter = settingAdapter
        }

        settingAdapter?.onClickItem = { affirmations ->
            if (affirmations.fragment != null) {
                openFragment(affirmations.fragment, null, true)
            }
        }
    }
}