package com.example.daily.ui.fragment.settingDaiLy.layoutSetting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
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
        binding.rvSetting.apply {
            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            layoutManager = gridLayoutManager
            settingAdapter = SettingAdapter(DataB.listSettings)
            adapter = settingAdapter
        }
        settingAdapter.onClickItem = { setting ->
            when (setting.title) {
                "General" -> {

                }

                "Reminders" -> {

                }

                "Widgets" -> {

                }
            }

        }


    }

    private fun affirmations() {
        binding.rvAffirmations.apply {
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager
            settingAdapter = SettingAdapter(DataB.listAffirmations)
            adapter = settingAdapter
        }
        settingAdapter?.onClickItem = { affirmations ->

            when (affirmations.title) {
                "Collection" -> {

                }

                "Add your own" -> {

                }

                "Search" -> {

                }

                "Favorite" -> {

                }
            }

        }
    }
}