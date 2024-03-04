package com.example.daily.ui.Themes

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.daily.MainActivity
import com.example.daily.R
import com.example.daily.databinding.FragmentThemesBinding
import com.example.daily.ui.Edit.fragment.EditFragment
import com.example.daily.ui.Themes.Model.ThemesModel
import com.example.daily.ui.Themes.Model.TitleBackgroundModel
import com.example.daily.ui.Themes.adapter.ThemesAdapter
import com.example.daily.ui.Themes.adapter.TitleBackgroundAdapter
import com.example.notisave.base.BaseFragment


class ThemesFragment :BaseFragment<FragmentThemesBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentThemesBinding {
        return FragmentThemesBinding.inflate(inflater)
    }

    private var listThemes : List<ThemesModel>?=null
    private var themesAdapter : ThemesAdapter?=null
    private var listTitleBg: List<TitleBackgroundModel>? = null
    private var titleBackgroundAdapter : TitleBackgroundAdapter?=null



    override fun init() {

    }

    override fun setUpView() {
        val imageUri = arguments?.getString("imageUri")
        Log.d("imageUri", "onViewCreated: $imageUri")
        imageUri?.let {
            binding.ivBg.setImageURI(Uri.parse(it))
        }
        setUpListener()
        setUpDataTitleBg()
        setUpDataBg()
    }

    private fun setUpDataBg() {
        listThemes = listOf(
            ThemesModel(R.drawable.bg_one,true),
            ThemesModel(R.drawable.bg_one,true),
            ThemesModel(R.drawable.bg_one,true),
            ThemesModel(R.drawable.bg_one,true),
            ThemesModel(R.drawable.bg_one,true),
            ThemesModel(R.drawable.bg_one,true),
            ThemesModel(R.drawable.bg_one,true),
            ThemesModel(R.drawable.bg_one,true)
            )
        val firstFourItems =  listThemes!!.take(4)
        binding.rvListBackground.apply {
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            layoutManager = staggeredGridLayoutManager
            themesAdapter = ThemesAdapter(firstFourItems)
            adapter = themesAdapter
        }
        themesAdapter?.onClickItem ={titleBg ->
            Log.d("dsad", "setUpDataTitleBg: $titleBg")
        }
        themesAdapter?.onClickViewAllItem ={
            (activity as MainActivity).replaceFragment(DetailBgTitleFragment())
        }

    }

    private fun setUpDataTitleBg() {
        listTitleBg = listOf(
            TitleBackgroundModel("Most popular"),
            TitleBackgroundModel("Free today"),
            TitleBackgroundModel("Plain"),
            TitleBackgroundModel("Animated"),
            TitleBackgroundModel("Spiritual"),
            TitleBackgroundModel("Iridescent"),
            TitleBackgroundModel("Soft oasis"),
            TitleBackgroundModel("Tropical"),
            TitleBackgroundModel("Flower"),
            TitleBackgroundModel("Gems"),
            TitleBackgroundModel("Timeless heritage"),
            TitleBackgroundModel("Window"),

        )
        binding.rvListTitleBackground.apply {
            val layoutParams=
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager=layoutParams
            titleBackgroundAdapter = TitleBackgroundAdapter(listTitleBg!!)
            adapter= titleBackgroundAdapter
        }
        titleBackgroundAdapter?.onClickItem ={titleBg ->
            Log.d("title", "setUpDataTitleBg: $titleBg")
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