package com.example.daily.ui.Setting.collections.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentCollectionsBinding
import com.example.daily.model.CollectionModel
import com.example.daily.ui.Setting.collections.CollectionsAdapter
import com.example.daily.ui.Setting.collections.viewModel.CollectionsViewModel
import com.example.notisave.base.BaseFragment


class CollectionsFragment : BaseFragment<FragmentCollectionsBinding>() {
    private lateinit var viewModel: CollectionsViewModel

    private var collectionAdapter: CollectionsAdapter? = null

    private var collectionsList: List<CollectionModel> = listOf()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCollectionsBinding {
        return FragmentCollectionsBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(CollectionsViewModel::class.java)
        setDataRecycleView()
    }

    private fun setDataRecycleView() {
        binding.rvCollection.apply {
            val layoutParams = LinearLayoutManager(requireContext())
            layoutManager = layoutParams
            collectionAdapter = CollectionsAdapter(listOf())
            adapter = collectionAdapter
        }
        setData()
        collectionAdapter?.onClickItem={
            val bundle =Bundle()
            bundle.putString("nameCollection", it.nameCollection)

            val fragment =DetailCollectionsFragment().apply {
                arguments= bundle
            }
            (activity as MainActivity).replaceFragment(fragment)

        }

    }

    private fun setData() {
        viewModel.allCollections.observe(viewLifecycleOwner) { collections ->
            collections?.let {
                collectionsList = it
                collectionAdapter!!.setData(collectionsList)
            }
            if (collections.isEmpty()) {
                binding.rvCollection.visibility = View.GONE
                binding.ivNoData.visibility = View.VISIBLE
            } else {
                binding.rvCollection.visibility = View.VISIBLE
                binding.ivNoData.visibility = View.GONE

            }
        }

    }

    override fun setUpView() {
        setUpListener()

    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
        binding.btnAdd.setOnClickListener {
            (activity as MainActivity).replaceFragment(NewCollectionsFragment())
        }
    }

}