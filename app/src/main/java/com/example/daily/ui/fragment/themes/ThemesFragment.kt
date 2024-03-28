package com.example.daily.ui.fragment.themes

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentThemesBinding
import com.example.daily.model.EditModel
import com.example.daily.ui.activity.MainActivity
import com.example.daily.ui.fragment.mainFragment.MainFragment
import com.example.daily.ui.fragment.themes.edit.EditFragment
import com.example.daily.ui.fragment.themes.themBackground.background.model.ThemesModel
import com.example.daily.ui.fragment.themes.themBackground.background.adapter.ThemesAdapter
import com.example.daily.ui.fragment.themes.themBackground.background.DetailBgTitleFragment
import com.example.daily.ui.fragment.themes.themBackground.background.adapter.TitleBackgroundAdapter
import com.example.daily.util.DataB
import com.example.daily.util.KeyWord
import java.io.Serializable

class ThemesFragment : BaseFragment<FragmentThemesBinding>() {

    private lateinit var preferences: Preferences

    private lateinit var viewModel: ThemesViewModel
    private var themesAdapter: ThemesAdapter? = null
    private var titleBackgroundAdapter: TitleBackgroundAdapter? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentThemesBinding {
        return FragmentThemesBinding.inflate(inflater)
    }

    override fun init() {
        preferences = Preferences.getInstance(requireContext())
        val bg = preferences.getString(KeyWord.imageBg)
        Glide.with(requireContext())
            .load(bg)
            .into(binding.ivBg)
        viewModel = ViewModelProvider(this).get(ThemesViewModel::class.java)
        viewModel.getThemesByTitle(KeyWord.mostPopular) { themesList, _ ->
            setUpDataBackground(themesList)
        }
    }

    override fun setUpView() {
        clickListener()
        setUpDataTitleBg()
    }

    private fun clickListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnEdit.setOnClickListener {
            openFragment(EditFragment::class.java, null, true)
        }

        binding.constraintRandom.setOnClickListener {
            viewModel.getThemesByRanDom(false) { themesList, _ ->
                val randomIndex = (0 until themesList.size).random()
                val randomTheme = themesList[randomIndex]
                val randomImage = randomTheme.image
                val bundle = Bundle().apply {
                    putString(KeyWord.randomImage, randomImage)
                }

                openFragment(MainFragment::class.java, bundle, true)
            }
        }
        binding.constraintNew.setOnClickListener {
            openFragment(EditFragment::class.java, null, true)
        }
    }

    private fun setUpDataTitleBg() {

        binding.rvListTitleBackground.apply {
            val layoutParams =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = layoutParams
            titleBackgroundAdapter = TitleBackgroundAdapter(DataB.listTitleBg!!)
            adapter = titleBackgroundAdapter
        }
        titleBackgroundAdapter?.onClickItem = { titleBg ->
            val title = titleBg.title
            viewModel.getThemesByTitle(title) { themesList, _ ->
                setUpDataBackground(themesList)
            }
        }
    }

    private fun setUpDataBackground(themesList: List<ThemesModel>) {
        binding.rvListBackground.apply {
            val layoutParams = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            layoutManager = layoutParams
            themesAdapter = ThemesAdapter(themesList)
            adapter = themesAdapter
        }
        themesAdapter?.onClickViewAllItem = {
            val bundle = Bundle().apply {
                putString(KeyWord.titleBg, it.TitleBg)
                putSerializable(KeyWord.themesList, themesList as Serializable)
            }
            var detailBgTitleFragment = DetailBgTitleFragment().apply {
                arguments = bundle
            }
            (activity as MainActivity).replaceFragment(detailBgTitleFragment)
        }
    }

}