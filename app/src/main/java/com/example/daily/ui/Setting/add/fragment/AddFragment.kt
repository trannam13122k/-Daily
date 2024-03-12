package com.example.daily.ui.Setting.add.fragment


import Preferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentAddBinding
import com.example.daily.model.AddModel
import com.example.daily.ui.Setting.add.AddViewModel
import com.example.daily.ui.Setting.add.NewAddContentAdapter
import com.example.notisave.base.BaseFragment

class AddFragment :BaseFragment<FragmentAddBinding>(){

    private lateinit var viewModel: AddViewModel
    private lateinit var preferences: Preferences

    private var addContentAdapter : NewAddContentAdapter?=null


    private var listContent: List<AddModel> = listOf()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddBinding {
        return FragmentAddBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        preferences = Preferences.getInstance(requireContext())

    }

    override fun setUpView() {
        setUpDataRecycleView()
        setUpListener()
    }

    private fun setUpDataRecycleView() {
        binding.rvListAdd.apply {
            val layoutParams =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager=layoutParams
            addContentAdapter= NewAddContentAdapter(listOf())
            adapter= addContentAdapter
        }
        setUpData()
        addContentAdapter?.onClickItem = { item ->
            val bundle = Bundle()
            bundle.putLong("itemId", item.id) // Đặt dữ liệu vào Bundle, ở đây là ID của mục
            val fragment = AddContentCollectionsFragment()
            fragment.arguments = bundle

            (activity as MainActivity).replaceFragment(fragment)
        }
        addContentAdapter?.onClickIsFavourite ={item->
            Log.d("isFavourite", "setUpDataRecycleView: $item")
        }

    }

    private fun setUpData() {
        viewModel.allContent.observe(viewLifecycleOwner) { collections ->
            collections?.let {
                listContent = it
                addContentAdapter!!.setData(listContent)
                val stringList: List<String> = listContent.map { addModel ->
                    addModel.nameAdd
                }
                preferences.saveList("list_my_affirmations", stringList)
            }
            if (collections.isEmpty()) {
                binding.rvListAdd.visibility = View.GONE
                binding.ivNoData.visibility=View.VISIBLE
            } else {
                binding.rvListAdd.visibility = View.VISIBLE
                binding.ivNoData.visibility=View.GONE
            }
        }
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
        binding.btnAdd.setOnClickListener{
            (activity as MainActivity).replaceFragment(NewAddFragment())
        }
    }

}