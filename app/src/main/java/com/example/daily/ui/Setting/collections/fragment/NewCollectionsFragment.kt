package com.example.daily.ui.Setting.collections.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentNewCollectionBinding
import com.example.daily.model.CollectionModel
import com.example.daily.ui.Setting.collections.viewModel.CollectionsViewModel
import com.example.notisave.base.BaseFragment

class NewCollectionsFragment :BaseFragment<FragmentNewCollectionBinding>() {
    lateinit var viewModel : CollectionsViewModel
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewCollectionBinding {
       return FragmentNewCollectionBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(CollectionsViewModel::class.java)
    }

    override fun setUpView() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
        binding.btnSave.setOnClickListener {
            handleBtnSave()
        }
    }

    private fun handleBtnSave() {
        val text = binding.edtAdd.text.toString().trim()
        if(text.isEmpty()){
            Toast.makeText(requireContext(),"Vui lòng nhập dữ liệu vào edit text",Toast.LENGTH_SHORT).show()
        }
        else{
            val newCollection = CollectionModel(nameCollection = text)
            viewModel.insertCollections(newCollection)
            (activity as MainActivity).supportFragmentManager.popBackStack()

        }

    }
}