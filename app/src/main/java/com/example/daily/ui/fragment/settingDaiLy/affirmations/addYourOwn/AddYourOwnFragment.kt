package com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentAddYourOwnBinding
import com.example.daily.model.AddModel
import com.example.daily.model.FavouriteModel
import com.example.daily.ui.activity.MainActivity
import com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn.addContentCollectionsFragment.AddContentCollectionsFragment
import com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn.newAddDataYourOwn.NewAddFragment
import com.example.daily.util.KeyWord

class AddYourOwnFragment : BaseFragment<FragmentAddYourOwnBinding>() {

    private lateinit var viewModel: AddYourOwnViewModel
    private lateinit var preferences: Preferences

    private var addContentAdapter: AddYourOwnAdapter? = null
    private var listContent: List<AddModel> = listOf()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddYourOwnBinding {
        return FragmentAddYourOwnBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(AddYourOwnViewModel::class.java)
        preferences = Preferences.getInstance(requireContext())
    }

    override fun setUpView() {
        setUpDataRecycleView()
        setUpListener()
    }

    private fun setUpDataRecycleView() {
        binding.rvListAdd.apply {
            val layoutParams = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = layoutParams
            addContentAdapter = AddYourOwnAdapter(listOf())
            adapter = addContentAdapter
        }
        setUpData()
        addContentAdapter?.onClickItem = { item ->
            val bundle = Bundle()
            bundle.putLong(KeyWord.itemId, item.id)
            val fragment = AddContentCollectionsFragment()
            fragment.arguments = bundle
            openFragment(AddContentCollectionsFragment::class.java, null, false)
        }
        addContentAdapter?.onClickIsFavourite = { item ->
            val editFavourite = AddModel(
                id = item.id,
                nameAdd = item.nameAdd,
                nameCollection = item.nameCollection,
                isFavourite = item.isFavourite,
                day = item.day
            )

            viewModel.updateContent(editFavourite)
            if (item.isFavourite) {
                val newFavourite =
                    FavouriteModel(nameFavourite = item.nameAdd, isFavourite = true, day = item.day)
                viewModel.insertFavourite(newFavourite)
            } else {
                viewModel.deleteFavourite(item.nameAdd)
            }
            val stringList: List<String> = viewModel.allListFavourite.value?.map { favoritesModel ->
                favoritesModel.nameFavourite
            } ?: listOf()
            preferences.saveList(KeyWord.myListKey, stringList)
        }
        addContentAdapter?.onClickDialog = { item ->
            showDialog(item)
        }


    }

    private fun showDialog(item: AddModel) {
        val dialog = Dialog(requireContext(), R.style.CustomDialogStyle)
        val view = layoutInflater.inflate(R.layout.dialog_delete_edit, null)
        dialog.setContentView(view)

        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams().apply {
                copyFrom(window.attributes)
                width = (resources.displayMetrics.widthPixels * 0.8).toInt()
                height = WindowManager.LayoutParams.WRAP_CONTENT
                gravity = Gravity.CENTER
            }
            window.attributes = layoutParams
        }


        val btnEdit = view.findViewById<AppCompatButton>(R.id.btnEdit)
        val btnRemove = view.findViewById<AppCompatButton>(R.id.btnRemove)

        btnEdit.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(KeyWord.addModel, item)
            }
            val newAddFragment = NewAddFragment().apply {
                arguments = bundle
            }
            (activity as MainActivity).replaceFragment(newAddFragment)
            dialog.dismiss()

        }
        btnRemove.setOnClickListener {
            val deleteContent = AddModel(
                id = item.id,
                nameAdd = item.nameAdd,
                nameCollection = item.nameCollection,
                isFavourite = item.isFavourite,
                day = item.day
            )
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
                preferences.saveList(KeyWord.list_my_affirmations, stringList)
            }
            if (collections.isEmpty()) {
                binding.rvListAdd.visibility = View.GONE
                binding.ivNoData.visibility = View.VISIBLE

            } else {
                binding.rvListAdd.visibility = View.VISIBLE
                binding.ivNoData.visibility = View.GONE
            }
        }
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnAdd.setOnClickListener {
            (activity as MainActivity).replaceFragment(NewAddFragment())
        }
    }


}