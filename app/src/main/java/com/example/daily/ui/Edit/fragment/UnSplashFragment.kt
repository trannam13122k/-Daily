package com.example.daily.ui.Edit.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.daily.MainActivity
import com.example.daily.R
import com.example.daily.databinding.FragmentUnsplashBinding
import com.example.daily.ui.Edit.adapter.UnSplashAdapter
import com.example.daily.ui.Edit.model.UnsplashModel
import com.example.notisave.base.BaseFragment

class UnSplashFragment :BaseFragment<FragmentUnsplashBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUnsplashBinding {
        return FragmentUnsplashBinding.inflate(inflater)
    }

    private var listUnsplash : List<UnsplashModel>?=null
    private var unSplashAdapter : UnSplashAdapter?=null


    override fun init() {
    }

    override fun setUpView() {
        setUpDataRVStart()
        setUpListener()
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
    }


    private fun setUpDataRVStart() {
        listUnsplash = listOf(
            UnsplashModel(R.drawable.bg_one),
            UnsplashModel(R.drawable.bg_two),
            UnsplashModel(R.drawable.bg_three),
            UnsplashModel(R.drawable.bg_five),
            UnsplashModel(R.drawable.bg_four),
            UnsplashModel(R.drawable.bg_six),
            UnsplashModel(R.drawable.bg_seven),
            UnsplashModel(R.drawable.bg_eight),
            UnsplashModel(R.drawable.bg_nine)
        )
        binding.rvUnSplashStart.apply {
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            layoutManager = staggeredGridLayoutManager
            unSplashAdapter = UnSplashAdapter(listUnsplash)
            adapter = unSplashAdapter
        }

    }

}