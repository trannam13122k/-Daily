package com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.detailCollections

import android.app.AlertDialog
import android.content.Context
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
import com.example.daily.model.CollectionModel
import com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn.AddYourOwnAdapter
import com.example.daily.ui.fragment.settingDaiLy.affirmations.collections.CollectionsViewModel
import com.example.daily.util.KeyWord

class DetailCollectionsFragment : BaseFragment<FragmentDetailCollectionsBinding>() {

    private lateinit var viewModel: CollectionsViewModel
    private lateinit var preferences: Preferences

    private var detailAdapter: AddYourOwnAdapter? = null
    private var listContent: List<AddModel> = listOf()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailCollectionsBinding {
        return FragmentDetailCollectionsBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(CollectionsViewModel::class.java)
        preferences = Preferences.getInstance(requireContext())
    }

    override fun setUpView() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        setOnClickListener()
        setDataRecycleView()
    }

    private fun setOnClickListener() {
        binding.ivMore.setOnClickListener {
            showAlertDialog(
                requireContext(),
                "Notification",
                "Do you want to delete the collection?"
            )
        }
    }

    private fun showAlertDialog(context: Context, title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton(KeyWord.delete) { dialog, _ ->
            val nameCollection = arguments?.getString(KeyWord.nameCollection)
            val idCollection = arguments?.getLong(KeyWord.idCollection)
            var delete = CollectionModel(id = idCollection!!, nameCollection = nameCollection!!)
            viewModel.deleteCollection(delete)
            activity?.onBackPressed()
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun setDataRecycleView() {
        binding.rvListAdd.apply {
            val layoutParams = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = layoutParams
            detailAdapter = AddYourOwnAdapter(listOf())
            adapter = detailAdapter
        }
        setUpData()
    }

    private fun setUpData() {
        val nameCollection = arguments?.getString(KeyWord.nameCollection)
        binding.tvNameCollection.text = nameCollection
        nameCollection?.let { collection ->
            viewModel.getItemsByCollection(collection).observe(viewLifecycleOwner) { collections ->
                collections?.let { listContent ->
                    this.listContent = listContent
                    detailAdapter?.setData(listContent)
                    val stringList: List<String> = listContent.map { addModel ->
                        addModel.nameAdd
                    }
                    preferences.saveList(KeyWord.list_content_by_collection, stringList)
                }
                if (collections.isNullOrEmpty()) {
                    binding.rvListAdd.visibility = View.GONE
                    binding.ivNoData.visibility = View.VISIBLE
                } else {
                    binding.rvListAdd.visibility = View.VISIBLE
                    binding.ivNoData.visibility = View.GONE
                }
            }
        }
    }


}