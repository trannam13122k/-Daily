package com.example.daily.ui.Setting.collections.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.databinding.FragmentDetailCollectionsBinding
import com.example.daily.model.AddModel
import com.example.daily.ui.Setting.add.NewAddContentAdapter
import com.example.daily.ui.Setting.collections.viewModel.CollectionsViewModel
import com.example.notisave.base.BaseFragment

class DetailCollectionsFragment :BaseFragment<FragmentDetailCollectionsBinding>() {

    private lateinit var viewModel: CollectionsViewModel

    private var detailAdapter : NewAddContentAdapter?=null

    private var listContent: List<AddModel> = listOf()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailCollectionsBinding {
        return FragmentDetailCollectionsBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(CollectionsViewModel::class.java)
        setDataRecycleView()
    }

    private fun setDataRecycleView() {
        binding.rvListAdd.apply {
            val layoutParams =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager=layoutParams
            detailAdapter= NewAddContentAdapter(listOf())
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

    override fun setUpView() {

    }
}