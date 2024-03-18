package com.example.daily.ui.fragment.themes.themBackground.background

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentDetailBgTitleBinding
import com.example.daily.ui.fragment.mainFragment.MainFragment
import com.example.daily.ui.fragment.themes.themBackground.background.adapter.DetailBgTitleAdapter
import com.example.daily.ui.fragment.themes.themBackground.background.model.ThemesModel


class DetailBgTitleFragment : BaseFragment<FragmentDetailBgTitleBinding>() {

    private lateinit var preferences: Preferences

    private var themesList: List<ThemesModel>? = null
    private var detailBgTitleAdapter: DetailBgTitleAdapter? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBgTitleBinding {
        return FragmentDetailBgTitleBinding.inflate(inflater)
    }

    override fun init() {
        preferences = Preferences.getInstance(requireContext())

        val titleBg = arguments?.getString("titleBg")
        Log.d("titleBg", "init: $titleBg")
        themesList = arguments?.getSerializable("themesList") as? List<ThemesModel>
        if (themesList != null) {

            Log.d("themesList", "Received themes list: $themesList")
        }
        binding.tvTitleBg.text = titleBg
        setUpDataRecycleview()
    }

    override fun setUpView() {
        setUpListener()
    }


    private fun setUpDataRecycleview() {
        binding.rvListBg.apply {
            var layoutParams = GridLayoutManager(requireContext(), 2)
            layoutManager = layoutParams
            detailBgTitleAdapter = DetailBgTitleAdapter(themesList)
            adapter = detailBgTitleAdapter

            detailBgTitleAdapter!!.onClickItem = { background ->
                preferences.setString("imageBg", background.image)
                openFragment(MainFragment::class.java, null, false)
            }
        }

    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }


}