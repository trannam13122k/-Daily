package com.example.daily.ui.fragment.settingDaiLy.affirmations.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.R
import com.example.daily.databinding.ItemNewAddBinding
import com.example.daily.model.FavouriteModel

class FavoritesAdapter(private var list: List<FavouriteModel>?) :
    RecyclerView.Adapter<FavoritesAdapter.CollectionsViewHolder>() {

    var onClickItem: ((FavouriteModel) -> Unit)? = null
    var onClickIsFavourite: ((FavouriteModel) -> Unit)? = null

    inner class CollectionsViewHolder(val binding: ItemNewAddBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavouriteModel) {
            with(binding) {
                tvContent.text = data.nameFavourite
                tvDay.text = data.day

                ivFavourite.setImageResource(if (data.isFavourite) R.drawable.baseline_favorite_24 else R.drawable.icon_favourite)
                ivFavourite.setOnClickListener {
                    data.isFavourite = !data.isFavourite
                    ivFavourite.setImageResource(if (data.isFavourite) R.drawable.icon_favourite_add else R.drawable.baseline_favorite_24)
                    onClickIsFavourite?.invoke(data)
                }

                ivMore.visibility = View.GONE
                ivSave.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesAdapter.CollectionsViewHolder {
        val view = ItemNewAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    fun setData(newList: List<FavouriteModel>?) {
        list = newList
        notifyDataSetChanged()
    }
}