package com.example.daily.ui.Categories

import Preferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.MainActivity
import com.example.daily.database.firebase.Content
import com.example.daily.databinding.FragmentCategoriesBinding
import com.example.daily.ui.Home.HomeFragment
import com.example.daily.ui.Themes.Model.TitleBackgroundModel
import com.example.daily.ui.Themes.adapter.TitleBackgroundAdapter
import com.example.notisave.base.BaseFragment
import java.io.Serializable

class CategoriesFragment :BaseFragment<FragmentCategoriesBinding>() {
    private var categoriesAdapter : CategoriesAdapter?=null

    private var listCategories :List<Content>?=null

    private var titleContentAdapter: TitleBackgroundAdapter? = null
    private var listTitleContent: List<TitleBackgroundModel>? = null

    private lateinit var preferences: Preferences

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoriesBinding {
        return FragmentCategoriesBinding.inflate(inflater)
    }


    override fun init() {
        preferences = Preferences.getInstance(requireContext())

    }

    override fun setUpView() {
        setUpDataTitleBg()
        setUpDataRecycleView()
        setUpListener()
    }

    private fun setUpDataTitleBg() {
        listTitleContent = listOf(
            TitleBackgroundModel("Most popular"),
            TitleBackgroundModel("Improve your relationships"),
            TitleBackgroundModel("Throught provoking"),
            TitleBackgroundModel("Improve your mindset"),
            TitleBackgroundModel("Look on the bright side"),
            TitleBackgroundModel("Stay mentally strong"),
        )
        binding.rvTitleContent.apply {
            val layoutParams =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = layoutParams
            titleContentAdapter = TitleBackgroundAdapter(listTitleContent!!)
            adapter = titleContentAdapter
        }
        titleContentAdapter?.onClickItem = { titleBg ->
            val title = titleBg.title
            }

        }


    private fun setUpDataRecycleView() {
        listCategories = listOf(
            Content("General", "true", listOf("123", "456"), ""),
            Content("My favorite", "true", listOf("456", "789"), ""),
            Content("My affirmations", "true", listOf("dsdsd", "dsds"), ""),
            Content("My collection", "true", listOf("dsds12232", "456"), ""),
        )
        binding.rvContentDefault.apply {
            val layoutParams = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager=layoutParams
            categoriesAdapter= CategoriesAdapter(listCategories)
            adapter=categoriesAdapter
            Log.d("listCategories", "setUpDataRecycleView: $listCategories")
        }
        categoriesAdapter?.onClickItem ={
            preferences.setString("titleContent",it.titleContent).toString()
            preferences.saveList("myListKey", it.listContent)
            Log.d("myListKey", "setUpDataRecycleView: ${it.listContent}")

            val bundle = Bundle().apply {
                putString("titleContent",it.titleContent)
                putSerializable("listContent", it.listContent as Serializable)
                Log.d("themesList", "setUpDataBackground: ${it.listContent}")

            }
            var homeFragment = HomeFragment().apply {
                arguments = bundle
            }
            (activity as MainActivity).replaceFragment(homeFragment)
        }
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
    }
}