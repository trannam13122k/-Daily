package com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn.addContentCollectionsFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentAddContentCollectionsBinding
import com.example.daily.model.CollectionModel
import com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.CollectionsAdapter
import com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.CollectionsViewModel
import com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.newCollections.NewCollectionsFragment
import com.example.daily.util.KeyWord

class AddContentCollectionsFragment : BaseFragment<FragmentAddContentCollectionsBinding>() {

    private lateinit var viewModel: CollectionsViewModel
    private lateinit var preferences: Preferences

    private var collectionAdapter: CollectionsAdapter? = null
    private var collectionsList: List<CollectionModel> = listOf()

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
        binding.btnAdd.setOnClickListener {
            openFragment(NewCollectionsFragment::class.java,null,false)
        }
    }

    private fun setDataRecycleView() {
        binding.rvCollection.apply {
            layoutManager = LinearLayoutManager(requireContext())
            collectionAdapter = CollectionsAdapter(listOf())
            adapter = collectionAdapter
        }
        setData()
        collectionAdapter?.onClickItem = { collection ->
            preferences.setString(KeyWord.nameCollections, collection.nameCollection)
            val itemId = arguments?.getLong(KeyWord.itemId)
            Log.d("itemId", "setDataRecycleView: $itemId")
            if (itemId != null) {
                viewModel.updateNameCollection(itemId, collection.nameCollection)
            }
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