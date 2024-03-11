package com.example.daily.ui.fragment.themes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentThemesBinding
import com.example.daily.ui.fragment.edit.EditFragment
import com.example.daily.ui.fragment.themes.background.Themes
import com.example.daily.ui.fragment.themes.background.ThemesAdapter
import com.example.daily.ui.fragment.themes.titleBG.TitleBackground
import com.example.daily.ui.fragment.themes.titleBG.TitleBackgroundAdapter

class ThemesFragment : BaseFragment<FragmentThemesBinding>() {

    var titleBackgroundAdapter: TitleBackgroundAdapter? = null

    var themesAdapter: ThemesAdapter? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentThemesBinding {
        return FragmentThemesBinding.inflate(inflater)
    }

    override fun init() {
        clickListener()
    }

    override fun setUpView() {
        setDataTitleBG()
        setItemBG()
    }

    private fun clickListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnEdit.setOnClickListener {
            openFragment(EditFragment::class.java,null,true)
        }

    }

    private fun setDataTitleBG() {
        val listTitleBg = listOf(
            "Most popular",
            "Free today",
            "Plain",
            "Animated",
            "Spiritual",
            "Iridescent",
            "Soft oasis",
            "Tropical",
            "Flower",
            "Gems",
            "Timeless heritage",
            "Window"
        ).map { TitleBackground(it) }

        titleBackgroundAdapter = TitleBackgroundAdapter(listTitleBg!!)
        binding.rvListTitleBackground.apply {
            adapter = titleBackgroundAdapter
        }

        titleBackgroundAdapter?.onClickItem = { titleBg ->
            Toast.makeText(requireContext(), "Click title BG", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setItemBG() {

        val listThemes = mutableListOf<Themes>()

        for (i in 1..30) {
            listThemes.add(Themes(R.drawable.bg_one, true))
        }

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvListBackground.layoutManager = staggeredGridLayoutManager

        themesAdapter = ThemesAdapter(listThemes)
        binding.rvListBackground.adapter = themesAdapter

        themesAdapter!!.onClickItem = { titleBg ->
            Toast.makeText(requireContext(), "onClickItem BackGround", Toast.LENGTH_SHORT).show()
        }
        themesAdapter!!.onClickViewAllItem = {
            Toast.makeText(requireContext(), "onClickViewAllItem", Toast.LENGTH_SHORT).show()
        }
    }
}