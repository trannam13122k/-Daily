package com.example.daily.ui.fragment.settingDaiLy.affirmations.favourite

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentFavouriteBinding
import com.example.daily.model.FavouriteModel
import com.example.daily.util.KeyWord

class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>() {

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var preferences: Preferences

    private var adapterFavorites: FavoritesAdapter? = null
    private var listFavorites: List<FavouriteModel> = listOf()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavouriteBinding {
        return FragmentFavouriteBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
        preferences = Preferences.getInstance(requireContext())
    }

    override fun setUpView() {
        setUpDataListFavourite()
        setUpListener()
    }

    private fun setUpDataListFavourite() {
        binding.rvListFavorites.apply {
            val layoutParams = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = layoutParams
            adapterFavorites = FavoritesAdapter(listOf())
            adapter = adapterFavorites
        }

        adapterFavorites?.onClickIsFavourite = { favourite ->
            var deleteFavourite = FavouriteModel(favourite.id, favourite.nameFavourite, favourite.nameCollection,false, "")
            viewModel.deleteFavourite(deleteFavourite)
            viewModel.updateNameFavourite(favourite.nameFavourite,false)

        }

        viewModel.allFavourite.observe(viewLifecycleOwner) { collections ->
            collections?.let {
                listFavorites = it
                adapterFavorites!!.setData(listFavorites)
                val stringList: List<String> = listFavorites.map { favoritesModel ->
                    favoritesModel.nameFavourite
                }
                preferences.saveList(KeyWord.list_favorites, stringList)
            }
            if (collections.isEmpty()) {
                binding.rvListFavorites.visibility = View.GONE
                binding.ivNoData.visibility = View.VISIBLE

            } else {
                binding.rvListFavorites.visibility = View.VISIBLE
                binding.ivNoData.visibility = View.GONE
            }
        }
    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}