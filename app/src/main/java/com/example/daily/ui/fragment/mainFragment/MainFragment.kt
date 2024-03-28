package com.example.daily.ui.fragment.mainFragment

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentMainBinding
import com.example.daily.model.EditModel
import com.example.daily.ui.fragment.adapter.ViewPager2Adapter
import com.example.daily.ui.fragment.categories.CategoriesFragment
import com.example.daily.ui.fragment.settingDaiLy.settingMain.SettingFragment
import com.example.daily.ui.fragment.themes.ThemesFragment
import com.example.daily.ui.fragment.themes.edit.EditViewModel
import com.example.daily.util.DataB
import com.example.daily.util.DialogUtils
import com.example.daily.util.KeyWord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private var mListquestion: List<String>? = null
    private var listquestion: List<String>? = null
    private var adapter: ViewPager2Adapter? = null

    private lateinit var titleContent: String
    private lateinit var preferences: Preferences
    private lateinit var viewModel: EditViewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    override fun init() {
        preferences = Preferences.getInstance(requireContext())
        viewModel = ViewModelProvider(this)[EditViewModel::class.java]
    }

    override fun setUpView() {
        clickListener()
        viewPager2()
        setBackGround()
    }

    private fun setBackGround() {
        val randomImage = arguments?.getString(KeyWord.randomImage)
        if (randomImage != null) {
            Glide.with(requireContext())
                .load(randomImage)
                .into(binding.imgBgMain)
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                val edit = viewModel.getEdit()
                if (edit == null) {
                    val editAdd = EditModel(
                        imageBg = randomImage,
                        imageColor = 0,
                        imageUnSplashFragment = 0,
                        textColor = R.color.black,
                        font = R.font.amatic_bold,
                        size = 30,
                        alignment = Gravity.CENTER,
                        textTransform = ""
                    )
                    viewModel.insertEdit(editAdd)
                } else {
                    edit.imageBg = randomImage
                    viewModel.updateEdit(edit)
                }
            }
        } else {
            viewModel.allEdit.observe(viewLifecycleOwner) { editList ->
                if (editList.isNotEmpty()) {
                    val lastEdit = editList.last()
                    setBackGroundMain(lastEdit)
                }
            }
        }

        titleContent = preferences.getString(KeyWord.titleContent) ?: KeyWord.general

        binding.tvTitleContent.text = titleContent
    }

    private fun setBackGroundMain(lastEdit: EditModel) {
        if (lastEdit.imageBg.isNotEmpty()) {
            Glide.with(requireContext())
                .load(lastEdit.imageBg)
                .into(binding.imgBgMain)

        } else if (lastEdit.imageUnSplashFragment != 0) {
            binding.imgBgMain.setBackgroundResource(lastEdit.imageUnSplashFragment)
        } else if (lastEdit.imageColor != 0) {
            binding.imgBgMain.setBackgroundResource(lastEdit.imageColor)
        } else {
            binding.imgBgMain.setBackgroundColor(Color.YELLOW)
        }
    }

    override fun onResume() {
        super.onResume()
        preferences.setString(KeyWord.titleContent, titleContent!!)
    }

    private fun viewPager2() {
        titleContent = preferences.getString(KeyWord.titleContent) ?: KeyWord.general
        when (titleContent) {
            KeyWord.myFavorite -> {
                viewModel.allFavourite.observe(viewLifecycleOwner) {
                    if (it.isEmpty()) {
                        val stringList: List<String> = listOf()
                        listquestion = stringList
                        DialogUtils.showDialog(
                            requireContext(),
                            "Notifications",
                            "No Data Favorite"
                        )
                    } else {
                        val stringList: List<String> = it.map { favoritesModel ->
                            favoritesModel.nameFavourite
                        }
                        listquestion = stringList
                    }
                    adapter = ViewPager2Adapter(requireActivity(), listquestion!!)
                    binding.viewPager2.adapter = adapter
                    binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
                }
            }

            KeyWord.myAffirmations -> {
                viewModel.allAdd.observe(viewLifecycleOwner) {
                    if (it.isEmpty()) {
                        val stringList: List<String> = listOf()
                        listquestion = stringList
                        DialogUtils.showDialog(
                            requireContext(),
                            "Notifications",
                            "No Data Content User"
                        )
                    } else {
                        val stringList: List<String> = it.map { allContent ->
                            allContent.nameAdd
                        }
                        listquestion = stringList
                    }
                    adapter = ViewPager2Adapter(requireActivity(), listquestion!!)
                    binding.viewPager2.adapter = adapter
                    binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
                }
            }

            else -> {
                mListquestion = preferences.getList(KeyWord.myListKey)
                if (mListquestion == null) {
                    mListquestion = DataB?.listDataLocal
                }
                adapter = ViewPager2Adapter(requireActivity(), mListquestion!!)
                binding.viewPager2.adapter = adapter
                binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
            }
        }
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