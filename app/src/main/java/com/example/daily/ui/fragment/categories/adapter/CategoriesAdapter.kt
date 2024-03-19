package com.example.daily.ui.fragment.categories.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.daily.databinding.ItemCategoriesBinding
import com.example.daily.ui.fragment.categories.model.Content

class CategoriesAdapter (private val list : List<Content>?): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>(){
    var onClickItem: ((Content) -> Unit)? = null

    inner class CategoriesViewHolder(val binding: ItemCategoriesBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : Content){
            binding.tvTitle.text=data.titleContent
            binding.ivLock.visibility= View.GONE
            Glide.with(binding.ivIcon.context).load(data.icon).into(binding.ivIcon)
            binding.root.setOnClickListener{
                onClickItem?.invoke(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view= ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(list!![position])
    }


    override fun getItemCount(): Int {
        return list!!.size
    }
}