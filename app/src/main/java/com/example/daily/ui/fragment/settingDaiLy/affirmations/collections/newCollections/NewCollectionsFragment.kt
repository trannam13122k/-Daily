package com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.newCollections

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentNewCollectionsBinding
import com.example.daily.model.CollectionModel
import com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.CollectionsViewModel

class NewCollectionsFragment : BaseFragment<FragmentNewCollectionsBinding>() {

    lateinit var viewModel: CollectionsViewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewCollectionsBinding {
        return FragmentNewCollectionsBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this)[CollectionsViewModel::class.java]
    }

    override fun setUpView() {
        clickListener()
    }

    private fun clickListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            handleBtnSave()
        }
    }

    private fun handleBtnSave() {
        val text = binding.edtAdd.text.toString().trim()
        if (text.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter data into EditText", Toast.LENGTH_SHORT).show()
        } else {
            val newCollection = CollectionModel(nameCollection = text)
            viewModel?.let { viewModel ->
                viewModel.insertCollections(newCollection)
            }
            activity?.onBackPressed()
        }
    }

}