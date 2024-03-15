package com.example.daily.ui.Setting.favorites


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.MainActivity
import com.example.daily.databinding.FragmentFavoritesBinding
import com.example.daily.model.FavouriteModel
import com.example.notisave.base.BaseFragment

class FavoritesFragment :BaseFragment<FragmentFavoritesBinding>() {

    private lateinit var viewModel: FavoritesViewModel

    private var adapterFavorites :FavoritesAdapter?=null

    private var listFavorites : List<FavouriteModel> = listOf()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoritesBinding {
        return FragmentFavoritesBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

    }

    override fun setUpView() {
        setUpDataRecycleView()
        setUpListener()
    }

    private fun setUpDataRecycleView() {
        binding.rvListFavorites.apply {
            val layoutParams =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager=layoutParams
            adapterFavorites= FavoritesAdapter(listOf())
            adapter= adapterFavorites
        }
        setUpData()

//        addContentAdapter?.onClickItem = { item ->
//            val bundle = Bundle()
//            bundle.putLong("itemId", item.id) // Đặt dữ liệu vào Bundle, ở đây là ID của mục
//            val fragment = AddContentCollectionsFragment()
//            fragment.arguments = bundle
//
//            (activity as MainActivity).replaceFragment(fragment)
//        }
//        addContentAdapter?.onClickIsFavourite ={item->
//            Log.d("isFavourite", "setUpDataRecycleView: $item")
//        }
    }

    private fun setUpData() {
        viewModel.allFavourite.observe(viewLifecycleOwner) { collections ->
            collections?.let {
                listFavorites = it
                adapterFavorites!!.setData(listFavorites)
//                val stringList: List<String> = listContent.map { addModel ->
//                    addModel.nameAdd
//                }
//                preferences.saveList("list_my_affirmations", stringList)
            }
            if (collections.isEmpty()) {
                binding.rvListFavorites.visibility = View.GONE
                binding.ivNoData.visibility= View.VISIBLE

            } else {
                binding.rvListFavorites.visibility = View.VISIBLE
                binding.ivNoData.visibility= View.GONE
            }
        }
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
    }
}