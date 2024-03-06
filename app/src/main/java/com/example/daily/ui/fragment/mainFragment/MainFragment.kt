package com.example.daily.ui.fragment.mainFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentMainBinding
import com.example.daily.model.Content
import com.example.daily.ui.fragment.adapter.ViewPager2Adapter
import com.example.daily.ui.fragment.categories.CategoriesFragment
import com.example.daily.ui.fragment.setting.SettingFragment
import com.example.daily.ui.fragment.themes.ThemesFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {

//    private var adapter: MainContentAdapter? = null

    var viewPager2Adapter : ViewPager2Adapter? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    override fun init() {
    }

    override fun setUpView() {
        clickListener()

        showDataRecycleView()

        viewPager2()
    }

    private fun viewPager2() {
//        viewPager2Adapter = ViewPager2Adapter(requireActivity())
//        binding.viewPager2.adapter = viewPager2Adapter

        val adapter = ViewPager2Adapter(requireActivity())
        binding.viewPager2.adapter = adapter

    }

    private fun showDataRecycleView() {
//        adapter = MainContentAdapter()
//        val linearLayoutManager =
//            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//        binding.rcvHome.setLayoutManager(linearLayoutManager)
//
//        adapter!!.setData(listDataContent())
//        binding.rcvHome.setAdapter(adapter)


    }

//    private fun listDataContent(): List<Content> {
//
//        return listOf(
//            Content(
//                0,
//                "There are wounds that don't bleed, but are very painful",
//            ),
//            Content(
//                1,
//                "There are wounds that don't bleed, but are very painful",
//            ),
//            Content(
//                2,
//                "There are wounds that don't bleed, but are very painful",
//                ),
//            Content(
//                3,
//                "There are wounds that don't bleed, but are very painful",
//                ),
//            Content(
//                4,
//                "There are wounds that don't bleed, but are very painful",
//                ),
//            Content(
//                5,
//                "There are wounds that don't bleed, but are very painful",
//                ),
//            Content(
//                6,
//                "There are wounds that don't bleed, but are very painful",
//                ),
//            Content(
//                7,
//                "There are wounds that don't bleed, but are very painful",
//            ),
//        )
//
//    }

    private fun clickListener() {

        binding.llGeneral.setOnClickListener {
            openFragment(CategoriesFragment::class.java, null, true)
        }

        binding.llPen.setOnClickListener {
            openFragment(ThemesFragment::class.java, null, true)
        }

        binding.llUser.setOnClickListener {
            openFragment(SettingFragment::class.java, null, true)
        }
    }

    fun onBackPressed() {
        activity?.finish()
    }

}