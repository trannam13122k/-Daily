package com.example.daily.ui.fragment.mainFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentMainBinding
import com.example.daily.ui.fragment.adapter.ViewPager2Adapter
import com.example.daily.ui.fragment.categories.CategoriesFragment
import com.example.daily.ui.fragment.settingDaiLy.settingMain.SettingFragment
import com.example.daily.ui.fragment.themes.ThemesFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {

//    private var adapter: MainContentAdapter? = null

    var adapter: ViewPager2Adapter? = null


    private  var listContent: List<String> ?=null
    private lateinit var preferences: Preferences
    private lateinit var viewModel: MainViewModel
    private var mListquestion: List<String>? = null
    lateinit var titleContent: String

    private var textColor: String = ""


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    override fun init() {
        preferences = Preferences.getInstance(requireContext())
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


//        listContent= preferences.getList("myListKey")
//
//
//        if (listContent == null) {
//            listContent = listOf("Abcd")
//        }

//        textColor = arguments?.getString("text_color") ?: "#000000"


        preferences = Preferences.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        titleContent= preferences.getString("titleContent") ?: "General"
        binding.tvTitleContent.text=titleContent
        listContent= preferences.getList("myListKey")
        if (listContent == null) {
            listContent = listOf("Abcd")
        }
        textColor = arguments?.getString("text_color") ?: "#000000"
        val bgColor = arguments?.getString("bg_color")
        val imageBg = preferences.getString("imageBg")

        Log.d("imageBg", "init: $imageBg")
        val font = arguments?.getInt("font")

        imageBg?.let { uriString ->
            Glide.with(requireContext())
                .load(imageBg)
                .into(binding.imgBgMain)
        }

    }


    override fun setUpView() {
        clickListener()
        viewPager2()
//        setBackGround()
    }

    private fun setBackGround() {

        // bg
        val imageBg = preferences.getString("imageBg")
        imageBg?.let { uriString ->
            Glide.with(requireContext())
                .load(imageBg)
                .into(binding.imgBgMain)
        }
        //set Category
        titleContent= preferences.getString("titleContent") ?: "General"
        binding.tvTitleContent.text=titleContent
    }

    override fun onResume() {
        super.onResume()
        preferences.setString("titleContent", titleContent!!)
        Log.d("titleContent", "${titleContent.toString()}")
    }

    private fun viewPager2() {

        mListquestion =preferences.getList("myListKey")
        adapter = ViewPager2Adapter(requireActivity(), mListquestion!!)
        binding.viewPager2.adapter = adapter
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
    }



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