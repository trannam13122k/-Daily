package com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.detailCollections

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
import com.example.daily.databinding.FragmentDetailCollectionsBinding
import com.example.daily.model.AddModel
import com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn.AddYourOwnAdapter
import com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.CollectionsViewModel

class DetailCollectionsFragment : BaseFragment<FragmentDetailCollectionsBinding>() {

    private lateinit var viewModel: CollectionsViewModel

    private var detailAdapter : AddYourOwnAdapter?=null

    private var listContent: List<AddModel> = listOf()

    private lateinit var preferences: Preferences

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailCollectionsBinding {
    return FragmentDetailCollectionsBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(CollectionsViewModel::class.java)
        preferences = Preferences.getInstance(requireContext())
        setDataRecycleView()
    }

    override fun setUpView() {

    }

    private fun setDataRecycleView() {
        binding.rvListAdd.apply {
            val layoutParams =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager=layoutParams
            detailAdapter= AddYourOwnAdapter(listOf())
            adapter= detailAdapter
        }
        setUpData()
    }

    private fun setUpData() {
        val nameCollection=arguments?.getString("nameCollection")
        binding.tvNameCollection.text=nameCollection
        viewModel.getItemsByCollection(nameCollection!!).observe(viewLifecycleOwner) { collections ->
            collections?.let {
                listContent = it
                detailAdapter!!.setData(listContent)
                val stringList: List<String> = listContent.map { addModel ->
                    addModel.nameAdd
                }
                preferences.saveList("list_content_by_collection", stringList)
                Log.d("stringList", "setUpDataRecycleView: $stringList")

            }
            if (collections.isEmpty()) {
                binding.rvListAdd.visibility = View.GONE
                binding.ivNoData.visibility= View.VISIBLE
            } else {
                binding.rvListAdd.visibility = View.VISIBLE
                binding.ivNoData.visibility= View.GONE

            }
        }
    }


}