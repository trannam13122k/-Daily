package com.example.daily.ui.Setting.add.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentNewAddBinding
import com.example.daily.model.AddModel
import com.example.daily.ui.Setting.add.AddViewModel
import com.example.notisave.base.BaseFragment

class NewAddFragment:BaseFragment<FragmentNewAddBinding>(){
    private lateinit var viewModel: AddViewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewAddBinding {
        return FragmentNewAddBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
    }

    override fun setUpView() {
        setUpListener()

    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
        binding.btnSave.setOnClickListener{
            handleBtnSave()
        }
    }

    private fun handleBtnSave() {
        val text = binding.edtAdd.text.toString().trim()
        if(text.isEmpty()){
            Toast.makeText(requireContext(),"Vui lòng nhập dữ liệu vào edit text", Toast.LENGTH_SHORT).show()
        }
        else{
            val newContent = AddModel(nameAdd = text, nameCollection = "", isFavourite = false)
            viewModel.insertContent(newContent)
            (activity as MainActivity).supportFragmentManager.popBackStack()

        }
    }

}