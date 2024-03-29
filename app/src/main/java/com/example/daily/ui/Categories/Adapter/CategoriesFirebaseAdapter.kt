package com.example.daily.ui.Categories.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.daily.databinding.ItemCategoriesBinding
import com.example.daily.ui.Categories.Model.Content
import com.example.daily.ui.Categories.Model.ContentModelFireBase

class CategoriesFirebaseAdapter (private val list : List<ContentModelFireBase>?): RecyclerView.Adapter<CategoriesFirebaseAdapter.CategoriesViewHolder>(){
    var onClickItem: ((ContentModelFireBase) -> Unit)? = null

    inner class CategoriesViewHolder(val binding: ItemCategoriesBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : ContentModelFireBase){
            binding.tvTitle.text=data.nameContent
            binding.ivLock.visibility = if (data.isKey) {
                View.VISIBLE // Hiển thị view nếu isKey là true
            } else {
                View.GONE // Ẩn view nếu isKey là false
            }
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