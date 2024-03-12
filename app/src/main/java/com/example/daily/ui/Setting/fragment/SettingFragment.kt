package com.example.daily.ui.Setting.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.daily.MainActivity
import com.example.daily.R
import com.example.daily.databinding.FragmentSettingBinding
import com.example.daily.ui.Setting.SettingAdapter
import com.example.daily.ui.Setting.SettingModel
import com.example.daily.ui.Setting.add.fragment.AddFragment
import com.example.daily.ui.Setting.collections.fragment.CollectionsFragment
import com.example.notisave.base.BaseFragment

class SettingFragment:BaseFragment<FragmentSettingBinding>() {
    private var listSetting: List<SettingModel>? = null
    private var listAffirmations: List<SettingModel>? = null

    private var settingAdapter : SettingAdapter?=null
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater)
    }


    override fun init() {

    }

    override fun setUpView() {
        setUpListener()
        handleDataSetting()
        handleDataAffirmations()
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
    }

    private fun handleDataAffirmations() {
        listAffirmations = listOf(
            SettingModel("Collection", R.drawable.icon_book_open),
            SettingModel("Add your own", R.drawable.feather),
//            SettingModel("Practice", R.drawable.icon_setting),
            SettingModel("Search", R.drawable.icon_search),
//            SettingModel("Past affirmations", R.drawable.icon_setting),
            SettingModel("Favorite", R.drawable.icon_favourite)
        )

        binding.rvAffirmations.apply {
            val gridLayoutManager = GridLayoutManager(context, 2) // 2 cột
            layoutManager = gridLayoutManager
            settingAdapter = SettingAdapter(listAffirmations)
            adapter = settingAdapter
        }
        settingAdapter?.onClickItem = { affirmations ->

            when (affirmations.title) {
                "Collection" -> {
                    val collectionsFragment = CollectionsFragment()
                    (activity as MainActivity).replaceFragment(collectionsFragment)
                }
                "Add your own" -> {
                    val addFragment = AddFragment()
                    (activity as MainActivity).replaceFragment(addFragment)
                }
                "Search" -> {
//                    val widgetsFragment = WidgetsFragment()
//                    (activity as MainActivity).replaceFragment(widgetsFragment)
                }
                "Favorite" -> {
                    val favoritesFragment = FavoritesFragment()
                    (activity as MainActivity).replaceFragment(favoritesFragment)
                }
            }

        }
    }

    private fun handleDataSetting() {
        listSetting = listOf(
            SettingModel("General", R.drawable.icon_setting),
            SettingModel("Reminders", R.drawable.icon_reminders),
            SettingModel("Widgets", R.drawable.icon_widgets)
        )

        binding.rvSetting.apply {
            val gridLayoutManager = GridLayoutManager(context, 2) // 2 cột
            layoutManager = gridLayoutManager
            settingAdapter = SettingAdapter(listSetting)
            adapter = settingAdapter
        }
        settingAdapter?.onClickItem = { setting ->

            when (setting.title) {
                "General" -> {
                }
                "Reminders" -> {
                    val remindersFragment = RemindersFragment()
                    (activity as MainActivity).replaceFragment(remindersFragment)
                }
                "Widgets" -> {
                    val widgetsFragment = WidgetsFragment()
                    (activity as MainActivity).replaceFragment(widgetsFragment)
                }
            }

        }

    }
}