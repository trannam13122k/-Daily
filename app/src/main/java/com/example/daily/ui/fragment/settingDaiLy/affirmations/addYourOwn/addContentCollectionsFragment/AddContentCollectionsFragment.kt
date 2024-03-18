package com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn.addContentCollectionsFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentAddContentCollectionsBinding
import com.example.daily.model.CollectionModel
import com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.CollectionsAdapter
import com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.CollectionsViewModel

class AddContentCollectionsFragment : BaseFragment<FragmentAddContentCollectionsBinding>() {

    private lateinit var viewModel: CollectionsViewModel

    private var collectionAdapter: CollectionsAdapter? = null

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
        clickListener()
        setDataRecycleView()
    }

    private fun clickListener() {
        binding.ivClose.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setDataRecycleView() {
        binding.rvCollection.apply {
            val layoutParams = LinearLayoutManager(requireContext())
            layoutManager = layoutParams
            collectionAdapter = CollectionsAdapter(listOf())
            adapter = collectionAdapter
        }
        setData()
        collectionAdapter?.onClickItem = {
            preferences.setString("NameCollections", it.nameCollection)
            val itemId = arguments?.getLong("itemId")
            Log.d("itemId", "setDataRecycleView: $itemId")
            viewModel.updateNameCollection(itemId!!, it.nameCollection)
          activity?.onBackPressed()
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
}