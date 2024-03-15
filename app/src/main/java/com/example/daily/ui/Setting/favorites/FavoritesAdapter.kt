package com.example.daily.ui.Setting.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.R
import com.example.daily.databinding.ItemNewAddBinding
import com.example.daily.model.FavouriteModel

class FavoritesAdapter (private var list : List<FavouriteModel>?): RecyclerView.Adapter<FavoritesAdapter.CollectionsViewHolder>(){
    var onClickItem: ((FavouriteModel) -> Unit)? = null
    var onClickIsFavourite: ((FavouriteModel) -> Unit)? = null
    inner class CollectionsViewHolder(val binding: ItemNewAddBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : FavouriteModel){
            binding.tvContent.text=data.nameFavourite
            binding.tvDay.text=data.day
//            binding.ivSave.setOnClickListener{
//                onClickItem?.invoke(data)
//            }
            binding.ivFavourite.setImageResource(if(data.isFavourite) R.drawable.icon_lock_favourite else R.drawable.icon_favourite)
            binding.ivFavourite.setOnClickListener {
                data.isFavourite=!data.isFavourite
                binding.ivFavourite.setImageResource(if(data.isFavourite) R.drawable.icon_favourite_add else R.drawable.icon_lock_favourite)
                onClickIsFavourite?.invoke(data)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapter.CollectionsViewHolder {
        val view= ItemNewAddBinding.inflate(LayoutInflater.from(parent.context), parent,false)
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