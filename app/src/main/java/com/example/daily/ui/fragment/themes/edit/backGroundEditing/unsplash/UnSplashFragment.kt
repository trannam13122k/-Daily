package com.example.daily.ui.fragment.themes.edit.backGroundEditing.unsplash

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentUnSplashBinding
import com.example.daily.model.EditModel
import com.example.daily.ui.activity.MainActivity
import com.example.daily.ui.fragment.mainFragment.MainFragment
import com.example.daily.ui.fragment.themes.edit.EditViewModel
import com.example.daily.util.DataB
import com.example.daily.util.DialogUtils
import com.example.daily.util.KeyWord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UnSplashFragment : BaseFragment<FragmentUnSplashBinding>() {

    private var unSplashAdapter: UnSplashAdapter? = null

    private lateinit var viewModel: EditViewModel
    private lateinit var preferences: Preferences


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUnSplashBinding {
        return FragmentUnSplashBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)
        preferences = Preferences.getInstance(requireContext())
    }

    override fun setUpView() {
        setUpData()
        setUpListener()
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setUpData() {

        binding.rvUnSplashStart.apply {
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            layoutManager = staggeredGridLayoutManager
            unSplashAdapter = UnSplashAdapter(DataB.listUnsplash)
            adapter = unSplashAdapter
        }

        unSplashAdapter?.onClickItem = { background ->
            preferences.setString(KeyWord.imageBg, background.image.toString())
            updateBackground(background.image)
        }

    }

    private fun updateBackground(background: Int) {
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val edit = viewModel.getEdit()
            if (edit == null) {
                var editAdd = EditModel(
                    imageBg = "",
                    imageColor = 0,
                    imageUnSplashFragment = background,
                    textColor = R.color.black,
                    font = R.font.amatic_bold,
                    size = 30,
                    alignment = Gravity.CENTER,
                    textTransform = ""
                )
                viewModel.insertEdit(editAdd)
            } else {
                edit.apply {
                    imageUnSplashFragment = background
                }
                viewModel.updateEdit(edit)

            }
            withContext(Dispatchers.Main) {

                openFragment(MainFragment::class.java, null, false)
            }
        }

    }


}