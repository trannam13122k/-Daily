package com.example.daily.ui.fragment.settingDaiLy.affirmations.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentFavouriteBinding
import com.example.daily.model.FavouriteModel

class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>() {

    private lateinit var viewModel: FavoritesViewModel

    private var adapterFavorites: FavoritesAdapter? = null

    private var listFavorites: List<FavouriteModel> = listOf()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavouriteBinding {
        return FragmentFavouriteBinding.inflate(inflater)
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
            layoutManager = layoutParams
            adapterFavorites = FavoritesAdapter(listOf())
            adapter = adapterFavorites
        }

        viewModel.allFavourite.observe(viewLifecycleOwner) { collections ->
            collections?.let {
                listFavorites = it
                adapterFavorites!!.setData(listFavorites)

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