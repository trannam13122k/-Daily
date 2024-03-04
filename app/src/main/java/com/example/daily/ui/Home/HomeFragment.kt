package com.example.daily.ui.Home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentHomeBinding
import com.example.daily.ui.Setting.fragment.SettingFragment
import com.example.daily.ui.Themes.ThemesFragment
import com.example.notisave.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var listContent: List<ContentModel>? = null
    private var homeAdapter: HomeAdapter? = null

    private var textColor: String = "" // Mặc định là màu đen

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun init() {
        textColor = arguments?.getString("text_color") ?: "#000000"
        val bgColor = arguments?.getString("bg_color")
        val imageBg = arguments?.getString("imageUri")
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
        // Thêm các onClickListener cho các phần khác nếu cần
    }

    private fun handleDataContent() {
        listContent = listOf(
            ContentModel("I am 1"),
            ContentModel("I am 2")
        )
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