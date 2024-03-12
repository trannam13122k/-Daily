package com.example.daily.ui.Setting.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.R
import com.example.daily.databinding.ItemNewAddBinding
import com.example.daily.model.AddModel

class NewAddContentAdapter (private var list : List<AddModel>?): RecyclerView.Adapter<NewAddContentAdapter.CollectionsViewHolder>(){
    var onClickItem: ((AddModel) -> Unit)? = null
    var onClickIsFavourite: ((AddModel) -> Unit)? = null

    var isFavourite = false
    inner class CollectionsViewHolder(val binding: ItemNewAddBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : AddModel){
            binding.tvContent.text=data.nameAdd
            binding.ivSave.setOnClickListener{
                onClickItem?.invoke(data)
            }
            binding.ivFavourite.setOnClickListener {
                data.isFavourite=!data.isFavourite
                binding.ivFavourite.setImageResource(if(data.isFavourite)R.drawable.icon_favourite_add else R.drawable.icon_lock_favourite)
                onClickIsFavourite?.invoke(data)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewAddContentAdapter.CollectionsViewHolder {
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