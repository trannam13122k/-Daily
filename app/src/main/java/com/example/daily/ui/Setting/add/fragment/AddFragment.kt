package com.example.daily.ui.Setting.add.fragment


import Preferences
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.MainActivity
import com.example.daily.R
import com.example.daily.databinding.FragmentAddBinding
import com.example.daily.model.AddModel
import com.example.daily.model.FavouriteModel
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
            Log.d("isFavourite", "setUpDataRecycleView: ${item.isFavourite}")
            val editFavourite = AddModel(id=item.id,nameAdd = item.nameAdd, nameCollection = item.nameCollection, isFavourite = item.isFavourite, day = item.day)
            viewModel.updateContent(editFavourite)

            if(item.isFavourite){
                val newFavourite = FavouriteModel(nameFavourite = item.nameAdd, isFavourite = true, day = item.day)
                viewModel.insertFavourite(newFavourite)

            }
            else{
                viewModel.deleteFavourite(item.nameAdd)
            }
        }
        addContentAdapter?.onClickDialog={ item->
            showDialog(item)
        }

    }

    private fun showDialog(item: AddModel) {
        val dialog = Dialog(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_delete_edit, null)
        val btnEdit= view.findViewById<AppCompatButton>(R.id.btnEdit)
        val btnRemove = view.findViewById<AppCompatButton>(R.id.btnRemove)
        dialog.setContentView(view)
        btnEdit.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("addModel", item)
                Log.d("showDialog", "showDialog: $item")
            }
            val newAddFragment =NewAddFragment().apply {
                arguments=bundle
            }
            (activity as MainActivity).replaceFragment(newAddFragment)
            dialog.dismiss()

        }
        btnRemove.setOnClickListener {
            val deleteContent = AddModel(id=item.id,nameAdd = item.nameAdd ,nameCollection =item.nameCollection , isFavourite = item.isFavourite, day = item.day)
            viewModel.deleteContent(deleteContent)
            dialog.dismiss()
        }
        dialog.setCancelable(true)
        dialog.show()
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