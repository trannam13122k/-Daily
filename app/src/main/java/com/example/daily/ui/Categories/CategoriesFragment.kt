package com.example.daily.ui.Categories

import Preferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.MainActivity
import com.example.daily.R
import com.example.daily.databinding.FragmentCategoriesBinding
import com.example.daily.ui.Categories.Adapter.CategoriesAdapter
import com.example.daily.ui.Categories.Adapter.CategoriesFirebaseAdapter
import com.example.daily.ui.Categories.Model.Content
import com.example.daily.ui.Categories.Model.ContentModelFireBase
import com.example.daily.ui.Categories.viewModel.CategoriesViewModel
import com.example.daily.ui.Home.HomeFragment
import com.example.daily.ui.Setting.add.fragment.AddFragment
import com.example.daily.ui.Themes.Model.TitleBackgroundModel
import com.example.daily.ui.Themes.adapter.TitleBackgroundAdapter
import com.example.notisave.base.BaseFragment

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {
    private var categoriesAdapter: CategoriesAdapter? = null
    private var categoriesFireBaseAdapter: CategoriesFirebaseAdapter? = null

    private var listCategories: List<Content>? = null
    private var listCategoriesByTitle: List<ContentModelFireBase>? = null

    private var titleContentAdapter: TitleBackgroundAdapter? = null
    private var listTitleContent: List<TitleBackgroundModel>? = null

    private lateinit var preferences: Preferences

    private lateinit var viewModel: CategoriesViewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoriesBinding {
        return FragmentCategoriesBinding.inflate(inflater)
    }


    override fun init() {
        preferences = Preferences.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)

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
            viewModel.getContentByTitle(title) { contentList, _ ->
                Log.d("contentList", "setUpDataTitleBg: $contentList")
                setUpContentByTitle(contentList)
            }
        }
    }

    private fun setUpContentByTitle(contentList: List<ContentModelFireBase>) {
        binding.rvContentByTitle.apply {
            val layoutParams = GridLayoutManager(requireContext(), 2)
            layoutManager = layoutParams
            categoriesFireBaseAdapter = CategoriesFirebaseAdapter(contentList)
            adapter = categoriesFireBaseAdapter
        }
        categoriesFireBaseAdapter?.onClickItem = {
            preferences.setString("titleContent", it.nameContent).toString()
            preferences.saveList("myListKey", it.listContent)
            (activity as MainActivity).replaceFragment(HomeFragment())
            Log.d("contentList", "setUpContentByTitle: ${it.listContent}")
        }

    }


    private fun setUpDataRecycleView() {
        listCategories = listOf(
            Content("General", "true", listOf("123", "456"), R.drawable.icon_general),
            Content("My favorite", "true", listOf("456", "789"), R.drawable.icon_favourite),
            Content("My affirmations", "true", listOf(), R.drawable.icon_user_content),
            Content("My collection", "true", arrayListOf("dsds12232", "456"), R.drawable.book_open),
        )
        binding.rvContentDefault.apply {
            val layoutParams = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = layoutParams
            categoriesAdapter = CategoriesAdapter(listCategories)
            adapter = categoriesAdapter
            Log.d("listCategories", "setUpDataRecycleView: $listCategories")
        }
        categoriesAdapter?.onClickItem = {
            when (it.titleContent) {
                "General" -> {
                    preferences.setString("titleContent", it.titleContent).toString()
                    preferences.saveList("myListKey", it.listContent)
                    (activity as MainActivity).replaceFragment(HomeFragment())
                }

                "My favorite" -> {}
                "My affirmations" -> {
                    it.listContent = preferences.getList("list_my_affirmations")
                    if (it.listContent == null) {
                        (activity as MainActivity).replaceFragment(AddFragment())
                    } else {
                        preferences.setString("titleContent", it.titleContent).toString()
                        preferences.saveList("myListKey", it.listContent)
                        (activity as MainActivity).replaceFragment(HomeFragment())
                    }

                }

                "My collection" -> {}
            }
//


        }
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
    }
}