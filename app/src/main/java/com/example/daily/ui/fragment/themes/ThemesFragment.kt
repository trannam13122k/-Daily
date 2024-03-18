package com.example.daily.ui.fragment.themes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentThemesBinding
import com.example.daily.ui.activity.MainActivity
import com.example.daily.ui.fragment.themes.edit.EditFragment
import com.example.daily.ui.fragment.themes.themBackground.background.model.ThemesModel
import com.example.daily.ui.fragment.themes.themBackground.background.adapter.ThemesAdapter
import com.example.daily.ui.fragment.themes.random.RandomFragment
import com.example.daily.ui.fragment.themes.themBackground.background.DetailBgTitleFragment
import com.example.daily.ui.fragment.themes.themBackground.background.adapter.TitleBackgroundAdapter
import com.example.daily.ui.fragment.themes.themBackground.background.model.TitleBackgroundModel
import com.example.daily.util.DataB
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
        val bg = preferences.getString("imageBg")
        Glide.with(requireContext())
            .load(bg)
            .into(binding.ivBg)
        viewModel = ViewModelProvider(this).get(ThemesViewModel::class.java)
        themesAdapter = ThemesAdapter(emptyList())
        viewModel.themes.observe(viewLifecycleOwner, Observer { listThemes ->
            Log.d("listThemes", "init: $listThemes")
            themesAdapter?.updateData(listThemes)
        })
        viewModel.fetchAllThemes()
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
            openFragment(RandomFragment::class.java, null, true)
        }

    }

    //rcv text ngang
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

    //rcv bg
    private fun setUpDataBackground(themesList: List<ThemesModel>) {
        binding.rvListBackground.apply {
            val layoutParams = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            layoutManager = layoutParams
            themesAdapter = ThemesAdapter(themesList)
            adapter = themesAdapter
        }
        themesAdapter?.onClickViewAllItem = {
            Log.d("it.titleBg", "setUpDataBackground: $themesList")
            val bundle = Bundle().apply {
                putString("titleBg", it.TitleBg)
                putSerializable("themesList", themesList as Serializable)
                Log.d("themesList", "setUpDataBackground: $themesList")

            }
            var detailBgTitleFragment = DetailBgTitleFragment().apply {
                arguments = bundle
            }
            (activity as MainActivity).replaceFragment(detailBgTitleFragment)
        }
    }


}