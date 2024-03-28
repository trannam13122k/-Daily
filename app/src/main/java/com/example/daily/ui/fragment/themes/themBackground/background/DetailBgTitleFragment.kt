package com.example.daily.ui.fragment.themes.themBackground.background

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentDetailBgTitleBinding
import com.example.daily.model.EditModel
import com.example.daily.ui.fragment.mainFragment.MainFragment
import com.example.daily.ui.fragment.themes.edit.EditViewModel
import com.example.daily.ui.fragment.themes.themBackground.background.adapter.DetailBgTitleAdapter
import com.example.daily.ui.fragment.themes.themBackground.background.model.ThemesModel
import com.example.daily.util.DialogUtils
import com.example.daily.util.KeyWord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailBgTitleFragment : BaseFragment<FragmentDetailBgTitleBinding>() {

    private lateinit var preferences: Preferences
    private lateinit var viewModel: EditViewModel

    private var themesList: List<ThemesModel>? = null
    private var detailBgTitleAdapter: DetailBgTitleAdapter? = null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBgTitleBinding {
        return FragmentDetailBgTitleBinding.inflate(inflater)
    }

    override fun init() {
        preferences = Preferences.getInstance(requireContext())
        viewModel = ViewModelProvider(this)[EditViewModel::class.java]
        val titleBg = arguments?.getString(KeyWord.titleBg)
        themesList = arguments?.getSerializable(KeyWord.themesList) as? List<ThemesModel>
        binding.tvTitleBg.text = titleBg
        setUpDataRecycleview()
    }

    override fun setUpView() {
        setUpListener()
    }

    private fun setUpDataRecycleview() {
        binding.rvListBg.apply {
            var layoutParams = GridLayoutManager(requireContext(), 2)
            layoutManager = layoutParams
            detailBgTitleAdapter = DetailBgTitleAdapter(themesList)
            adapter = detailBgTitleAdapter

            detailBgTitleAdapter?.onClickItem = { background ->
                preferences.setString(KeyWord.imageBg, background.image)
                if (background.check) {
                    DialogUtils.showDialog(
                        requireActivity(),
                        "Notification",
                        "Would you like to spend 2 cents to use it?"
                    )
                } else {
                    updateBackground(background.image)
                }

            }
        }
    }

    private fun updateBackground(background: String) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val edit = viewModel.getEdit()
            if (edit == null) {
                var editAdd = EditModel(
                    imageBg = background,
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
                edit.apply {
                    imageBg = background
                }
                viewModel.updateEdit(edit)

            }
            withContext(Dispatchers.Main) {

                openFragment(MainFragment::class.java, null, false)
            }
        }

    }


    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }


}