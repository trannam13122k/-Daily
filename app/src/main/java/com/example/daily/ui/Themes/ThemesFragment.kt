package com.example.daily.ui.Themes

import Preferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentThemesBinding
import com.example.daily.ui.Edit.fragment.EditFragment
import com.example.daily.ui.Themes.Model.ThemesModel
import com.example.daily.ui.Themes.Model.TitleBackgroundModel
import com.example.daily.ui.Themes.adapter.ThemesAdapter
import com.example.daily.ui.Themes.adapter.TitleBackgroundAdapter
import com.example.daily.ui.Themes.viewModel.ThemesViewModel
import com.example.notisave.base.BaseFragment
import java.io.Serializable


class ThemesFragment : BaseFragment<FragmentThemesBinding>() {

    private lateinit var preferences: Preferences

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentThemesBinding {
        return FragmentThemesBinding.inflate(inflater)
    }

    private lateinit var viewModel: ThemesViewModel

    private var listThemes: List<ThemesModel>? = null

    private var themesAdapter: ThemesAdapter? = null
    private var listTitleBg: List<TitleBackgroundModel>? = null
    private var titleBackgroundAdapter: TitleBackgroundAdapter? = null


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
        setUpListener()
        setUpDataTitleBg()
    }

    private fun setUpDataTitleBg() {
        listTitleBg = listOf(
            TitleBackgroundModel("Most popular"),
            TitleBackgroundModel("Free today"),
//            TitleBackgroundModel("Plain"),
//            TitleBackgroundModel("Animated"),
//            TitleBackgroundModel("Spiritual"),
//            TitleBackgroundModel("Iridescent"),
//            TitleBackgroundModel("Soft oasis"),
//            TitleBackgroundModel("Tropical"),
//            TitleBackgroundModel("Flower"),
//            TitleBackgroundModel("Gems"),
//            TitleBackgroundModel("Timeless heritage"),
//            TitleBackgroundModel("Window"),

        )
        binding.rvListTitleBackground.apply {
            val layoutParams =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = layoutParams
            titleBackgroundAdapter = TitleBackgroundAdapter(listTitleBg!!)
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


    private fun setUpListener() {
        binding.btnEdit.setOnClickListener {
            (activity as MainActivity).replaceFragment(EditFragment())
        }
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }

    }

}