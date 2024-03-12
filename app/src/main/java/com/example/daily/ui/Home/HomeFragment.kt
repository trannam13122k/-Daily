package com.example.daily.ui.Home

import Preferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentHomeBinding
import com.example.daily.ui.Categories.CategoriesFragment
import com.example.daily.ui.Setting.fragment.SettingFragment
import com.example.daily.ui.Themes.ThemesFragment
import com.example.notisave.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var listContent: List<String>? = null
    private var listContent1: List<String>? = null
    private var homeAdapter: HomeAdapter? = null

    private var textColor: String = "" // Mặc định là màu đen

    private lateinit var preferences: Preferences
    lateinit var titleContent: String

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun init() {
        preferences = Preferences.getInstance(requireContext())
        titleContent= preferences.getString("titleContent") ?: "General"
        binding.tvTitleContent.text=titleContent
        listContent= preferences.getList("myListKey")
        if (listContent == null) {
            // Nếu listContent là null hoặc rỗng, khởi tạo nó với một danh sách chứa một phần tử "Abcd"
            listContent = listOf("Abcd")
        }
        textColor = arguments?.getString("text_color") ?: "#000000"
        val bgColor = arguments?.getString("bg_color")
//        val imageBg = arguments?.getString("imageUri")
        val imageBg = preferences.getString("imageBg")

        Log.d("imageBg", "init: $imageBg")
        val font = arguments?.getInt("font")
//        bgColor?.let {
//            binding.constraintBg.setBackgroundResource(Color.parseColor(it)) // Set background từ màu sắc nhận được
//        }
        imageBg?.let { uriString ->
            Glide.with(requireContext())
                .load(imageBg)
                .into(binding.ivBg)
        }
        handleDataContent()
    }

    override fun onResume() {
        super.onResume()
        preferences.setString("titleContent", titleContent!!)
        Log.d("titleContent", "${titleContent.toString()}")

    }

    override fun setUpView() {
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.llPen.setOnClickListener {
            val themesFragment = ThemesFragment()
            (activity as MainActivity).replaceFragment(themesFragment)
        }
        binding.llUser.setOnClickListener {
            val settingFragment = SettingFragment()
            (activity as MainActivity).replaceFragment(settingFragment)
        }
        binding.llGeneral.setOnClickListener {
            (activity as MainActivity).replaceFragment(CategoriesFragment())
        }
    }

    private fun handleDataContent() {
        binding.rvHome.apply {
            val layoutParams =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = layoutParams
            Log.d("textColor", "handleDataContent: $textColor")
            homeAdapter = HomeAdapter(listContent, textColor) // Truyền màu sắc cho HomeAdapter
            adapter = homeAdapter
        }
    }
}