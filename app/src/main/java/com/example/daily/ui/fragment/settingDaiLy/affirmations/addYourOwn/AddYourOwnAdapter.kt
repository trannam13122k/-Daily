package com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.R
import com.example.daily.databinding.ItemNewAddBinding
import com.example.daily.model.AddModel

class AddYourOwnAdapter (private var list : List<AddModel>?): RecyclerView.Adapter<AddYourOwnAdapter.CollectionsViewHolder>(){
    var onClickItem: ((AddModel) -> Unit)? = null
    var onClickIsFavourite: ((AddModel) -> Unit)? = null
    var onClickDialog: ((AddModel) -> Unit)? = null

    var isFavourite = false
    inner class CollectionsViewHolder(val binding: ItemNewAddBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : AddModel){
            binding.tvContent.text=data.nameAdd
            binding.tvDay.text=data.day
            binding.ivSave.setOnClickListener{
                onClickItem?.invoke(data)
            }
            binding.ivMore.setOnClickListener {
                onClickDialog?.invoke(data)
            }
            binding.ivSave.setImageResource(if(data.nameCollection=="") R.drawable.icon_save else R.drawable.icon_lock_save)
            binding.ivFavourite.setImageResource(if(data.isFavourite) R.drawable.icon_lock_favourite else R.drawable.icon_favourite_add)
            binding.ivFavourite.setOnClickListener {
                data.isFavourite=!data.isFavourite
                binding.ivFavourite.setImageResource(if(data.isFavourite) R.drawable.icon_lock_favourite else R.drawable.icon_favourite_add)
                onClickIsFavourite?.invoke(data)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddYourOwnAdapter.CollectionsViewHolder {
        val view= ItemNewAddBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return CollectionsViewHolder(view)
    }



    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.bind(list!![position])
    }


    override fun getItemCount(): Int {
        return list!!.size
    }
    fun setData(newList: List<AddModel>?) {
        list = newList
        notifyDataSetChanged()
    }
}