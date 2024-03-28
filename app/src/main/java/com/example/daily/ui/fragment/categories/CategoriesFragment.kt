package com.example.daily.ui.fragment.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
import com.example.daily.ui.fragment.themes.themBackground.background.adapter.TitleBackgroundAdapter
import com.example.daily.util.DataB
import com.example.daily.util.DialogUtils
import com.example.daily.util.KeyWord

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
        viewModel = ViewModelProvider(this)[CategoriesViewModel::class.java]
        viewModel.getContentByTitle(KeyWord.mostPopular) { contentList, _ ->
            setUpItemBySelect(contentList)
        }
    }

    override fun setUpView() {
        clickListener()
        setUpDataTitleSelect()
        setUpDataMixCategory()
    }

    private fun clickListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setUpDataTitleSelect() {
        binding.rcvTitleSelect.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            titleContentAdapter = DataB?.listTitleContent?.let { TitleBackgroundAdapter(it) }
            adapter = titleContentAdapter
        }
        titleContentAdapter?.onClickItem = { titleBg ->
            val title = titleBg.title
            viewModel.getContentByTitle(title) { contentList, _ ->
                setUpItemBySelect(contentList)
            }
        }
    }

    private fun setUpItemBySelect(contentList: List<ContentModelFireBase>) {
        binding.rvContentByTitle.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            categoriesFireBaseAdapter = CategoriesFirebaseAdapter(contentList).apply {
                adapter = this
                onClickItem = { item ->
                    if (item.check) {
                        showDialog(
                            item,
                            requireActivity(),
                            "Notification",
                            "Would you like to spend 2 cents to use it?"
                        )
                    } else {
                        preferences.setString("titleContent", item.nameContent).toString()
                        preferences.saveList(KeyWord.myListKey, item?.listContent)
                        (activity as MainActivity).replaceFragment(MainFragment())
                    }
                }
            }
        }
    }

    private fun showDialog(
        it: ContentModelFireBase,
        context: Context,
        title: String,
        message: String
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                preferences.setString(KeyWord.titleContent, it.nameContent).toString()
                preferences.saveList(KeyWord.myListKey, it?.listContent)
                openFragment(MainFragment::class.java, null, false)
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun setUpDataMixCategory() {
        binding.rcvMixCategory.apply {
            val layoutParams = GridLayoutManager(requireContext(), 2)
            layoutManager = layoutParams
            categoriesAdapter = CategoriesAdapter(DataB?.listCategories)
            adapter = categoriesAdapter

        }
//        categoriesAdapter?.onClickItem = {
//            when (it.titleContent) {
//                KeyWord.general -> {
//                    preferences.setString(KeyWord.titleContent, it.titleContent).toString()
//                    preferences.saveList(KeyWord.myListKey, it.listContent)
//
//                    openFragment(MainFragment::class.java, null, false)
//                }
//
//                KeyWord.myFavorite -> {
//                    it.listContent = preferences.getList(KeyWord.list_favorites)
//                    if (it.listContent == null) {
//                        DialogUtils.showDialog(
//                            requireActivity(),
//                            "Notification",
//                            "Currently your data is not available, please check it"
//                        )
//                    } else {
//                        preferences.setString(KeyWord.titleContent, it.titleContent).toString()
//                        preferences.saveList(KeyWord.myListKey, it.listContent)
//
//                        openFragment(MainFragment::class.java, null, false)
//                    }
//                }
//
//                KeyWord.myAffirmations -> {
//                    it.listContent = preferences.getList(KeyWord.list_my_affirmations)
//                    if (it.listContent?.isEmpty() == true) {
//                        openFragment(AddYourOwnFragment::class.java, null, false)
//                    } else {
//                        preferences.setString(KeyWord.titleContent, it.titleContent).toString()
//                        preferences.saveList(KeyWord.myListKey, it.listContent)
//                        openFragment(MainFragment::class.java, null, false)
//                    }
//                }
//
//                KeyWord.myCollection -> {
//                    (activity as MainActivity).replaceFragment(CollectionsFragment())
//                    it.listContent = preferences.getList(KeyWord.list_content_by_collection)
//                    if (it.listContent!!.isEmpty()) {
//                        openFragment(CollectionsFragment::class.java, null, false)
//                    } else {
//                        preferences.setString(KeyWord.titleContent, it.titleContent).toString()
//                        preferences.saveList(KeyWord.myListKey, it.listContent)
//                        openFragment(MainFragment::class.java, null, false)
//                    }
//                }
//            }
//        }
        categoriesAdapter?.onClickItem = { item ->
            item?.let {
                when (it.titleContent) {
                    KeyWord.general -> {
                        preferences.setString(KeyWord.titleContent, it.titleContent)
                        preferences.saveList(KeyWord.myListKey, it.listContent)

                        openFragment(MainFragment::class.java, null, false)
                    }

                    KeyWord.myFavorite -> {
                        preferences.setString(KeyWord.titleContent, it.titleContent)
//
                        openFragment(MainFragment::class.java, null, false)
                    }

                    KeyWord.myAffirmations -> {
                        preferences.setString(KeyWord.titleContent, it.titleContent)
                            openFragment(MainFragment::class.java, null, false)

                    }

                    KeyWord.myCollection -> {
                        it.listContent = preferences.getList(KeyWord.list_content_by_collection)
                        if (it.listContent?.isEmpty() == true) {
                            openFragment(CollectionsFragment::class.java, null, false)
                        } else {
                            preferences.setString(KeyWord.titleContent, it.titleContent)
                            preferences.saveList(KeyWord.myListKey, it.listContent)
                            openFragment(MainFragment::class.java, null, false)
                        }
                    }
                }
            }
        }
    }

}