package com.example.daily.ui.fragment.categories

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentCategoriesBinding
import com.example.daily.ui.activity.MainActivity
import com.example.daily.ui.fragment.categories.adapter.CategoriesAdapter
import com.example.daily.ui.fragment.categories.adapter.CategoriesFirebaseAdapter
import com.example.daily.ui.fragment.categories.model.ContentModelFireBase
import com.example.daily.ui.fragment.mainFragment.MainFragment
import com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn.AddYourOwnFragment
import com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.CollectionsFragment
import com.example.daily.ui.fragment.settingDaiLy.affirmations.favourite.FavouriteFragment
import com.example.daily.ui.fragment.themes.themBackground.background.adapter.TitleBackgroundAdapter
import com.example.daily.util.DataB

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {

    private var categoriesAdapter: CategoriesAdapter? = null
    private var categoriesFireBaseAdapter: CategoriesFirebaseAdapter? = null
    private var titleContentAdapter: TitleBackgroundAdapter? = null

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
        clickListener()
        setUpDataTitleBg()
        setUpDataRecycleView()
    }

    private fun clickListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setUpDataTitleBg() {

        binding.rvTitleContent.apply {
            val layoutParams = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = layoutParams
            titleContentAdapter = DataB?.listTitleContent?.let { TitleBackgroundAdapter(it) }
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
            preferences.saveList("myListKey", it?.listContent)
            (activity as MainActivity).replaceFragment(MainFragment())
            Log.d("contentList", "setUpContentByTitle: ${it.listContent}")
        }
    }

    private fun setUpDataRecycleView() {

        binding.rvContentDefault.apply {
            val layoutParams =GridLayoutManager(requireContext(), 2)
            layoutManager = layoutParams
            categoriesAdapter = CategoriesAdapter(DataB?.listCategories)
            adapter = categoriesAdapter
            Log.d("listCategories", "setUpDataRecycleView: ${DataB.listCategories}")

        }
        categoriesAdapter?.onClickItem = {
            when (it.titleContent) {
                "General" -> {
                    preferences.setString("titleContent", it.titleContent).toString()
                    preferences.saveList("myListKey", it.listContent)
                    (activity as MainActivity).replaceFragment(MainFragment())
                }

                "My favorite" -> {
                    (activity as MainActivity).replaceFragment(MainFragment())
                    it.listContent = preferences.getList("list_favorites")
                    Log.d("list", "setUpDataRecycleView: ${it.listContent}")
                    if (it.listContent == null) {
                        (activity as MainActivity).replaceFragment(FavouriteFragment())
                    } else {
                        preferences.setString("titleContent", it.titleContent).toString()
                        preferences.saveList("myListKey", it.listContent)
                        (activity as MainActivity).replaceFragment(MainFragment())
                    }

                }

                "My affirmations" -> {
                    it.listContent = preferences.getList("list_my_affirmations")
                    if (it.listContent == null) {
                        (activity as MainActivity).replaceFragment(AddYourOwnFragment())
                    } else {
                        preferences.setString("titleContent", it.titleContent).toString()
                        preferences.saveList("myListKey", it.listContent)
                        (activity as MainActivity).replaceFragment(MainFragment())
                    }
                }

                "My collection" -> {
                    (activity as MainActivity).replaceFragment(CollectionsFragment())
                    it.listContent = preferences.getList("list_content_by_collection")
                    Log.d("listContent", "setUpDataRecycleView: ${it.listContent}")
                    if (it.listContent == null) {
                        (activity as MainActivity).replaceFragment(AddYourOwnFragment())
                    } else {
                        preferences.setString("titleContent", it.titleContent).toString()
                        preferences.saveList("myListKey", it.listContent)
                        (activity as MainActivity).replaceFragment(MainFragment())
                    }
                }
            }


        }
    }

}