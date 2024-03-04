package com.example.daily.ui.Setting.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentCollectionsBinding
import com.example.daily.ui.Setting.collections.viewModel.CollectionsViewModel
import com.example.notisave.base.BaseFragment


class CollectionsFragment :BaseFragment<FragmentCollectionsBinding>() {
    private lateinit var viewModel: CollectionsViewModel

    private var collectionAdapter :CollectionsAdapter?=null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCollectionsBinding {
        return FragmentCollectionsBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(CollectionsViewModel::class.java)
        setData()
    }

    private fun setData() {
        collectionAdapter = CollectionsAdapter(listOf())
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false).apply {
                reverseLayout = true
                stackFromEnd = true
            }
        binding.rvCollection.apply {
            adapter = collectionAdapter
            setLayoutManager(layoutManager)
        }
    }

    override fun setUpView() {
        dataCollections()
        setUpListener()

    }

    private fun dataCollections() {

    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
    }

}