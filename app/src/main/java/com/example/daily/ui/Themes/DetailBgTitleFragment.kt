package com.example.daily.ui.Themes

import Preferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentDetailBgBinding
import com.example.daily.ui.Home.HomeFragment
import com.example.daily.ui.Themes.Model.ThemesModel
import com.example.daily.ui.Themes.adapter.DetailBgTitleAdapter
import com.example.daily.ui.Themes.adapter.ThemesAdapter
import com.example.notisave.base.BaseFragment

class DetailBgTitleFragment : BaseFragment<FragmentDetailBgBinding>() {

    private lateinit var preferences: Preferences

    private var themesList: List<ThemesModel>? = null
    private var detailBgTitleAdapter: DetailBgTitleAdapter? = null


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBgBinding {
        return FragmentDetailBgBinding.inflate(inflater)
    }

    override fun init() {
        preferences = Preferences.getInstance(requireContext())

        val titleBg = arguments?.getString("titleBg")
        Log.d("titleBg", "init: $titleBg")
        themesList = arguments?.getSerializable("themesList") as? List<ThemesModel>
        if (themesList != null) {
            // Xử lý danh sách ở đây
            Log.d("themesList", "Received themes list: $themesList")
        }
        binding.tvTitleBg.text = titleBg
        setUpDataRecycleview()
    }

    private fun setUpDataRecycleview() {
        binding.rvListBg.apply {
            var layoutParams = GridLayoutManager(requireContext(), 2)
            layoutManager = layoutParams
            detailBgTitleAdapter = DetailBgTitleAdapter(themesList)
            adapter = detailBgTitleAdapter

            detailBgTitleAdapter!!.onClickItem = { background ->
                preferences.setString("imageBg",background.image)
                (activity as MainActivity).replaceFragment(HomeFragment())


            }


        }

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