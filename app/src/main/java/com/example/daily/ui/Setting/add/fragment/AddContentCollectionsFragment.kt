package com.example.daily.ui.Setting.add.fragment

import Preferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentAddContentCollectionsBinding
import com.example.daily.model.CollectionModel
import com.example.daily.ui.Setting.collections.CollectionsAdapter
import com.example.daily.ui.Setting.collections.viewModel.CollectionsViewModel
import com.example.notisave.base.BaseFragment


class AddContentCollectionsFragment :BaseFragment<FragmentAddContentCollectionsBinding>() {
    private lateinit var viewModel: CollectionsViewModel

    private var collectionAdapter :CollectionsAdapter?=null

    private var collectionsList: List<CollectionModel> = listOf()

    private lateinit var preferences: Preferences

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddContentCollectionsBinding {
        return FragmentAddContentCollectionsBinding.inflate(inflater)
    }

    override fun init() {
        preferences = Preferences.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(CollectionsViewModel::class.java)
    }

    override fun setUpView() {
        setDataRecycleView()
    }

    private fun setDataRecycleView() {
        binding.rvCollection.apply {
            val layoutParams = LinearLayoutManager(requireContext())
            layoutManager= layoutParams
            collectionAdapter = CollectionsAdapter(listOf())
            adapter=collectionAdapter
        }
        setData()
        collectionAdapter?.onClickItem ={
            preferences.setString("NameCollections",it.nameCollection)
            val itemId = arguments?.getLong("itemId")
            Log.d("itemId", "setDataRecycleView: $itemId")
            viewModel.updateNameCollection(itemId!!, it.nameCollection)
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
    }

    private fun setData() {
        viewModel.allCollections.observe(viewLifecycleOwner) { collections ->
            collections?.let {
                collectionsList=it
                collectionAdapter!!.setData(collectionsList)
            }
            if (collections.isEmpty()) {
                binding.rvCollection.visibility = View.GONE
                binding.ivNoData.visibility= View.VISIBLE
            } else {
                binding.rvCollection.visibility = View.VISIBLE
                binding.ivNoData.visibility= View.GONE

            }
        }
    }
}