package com.example.daily.ui.Edit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemUnsplashBinding
import com.example.daily.ui.Edit.model.UnsplashModel

class UnSplashAdapter  (private val listUnSplash : List<UnsplashModel>?): RecyclerView.Adapter<UnSplashAdapter.UnSplashViewHolder>(){
    inner class UnSplashViewHolder(val binding: ItemUnsplashBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : UnsplashModel){
//            Glide.with(binding.ivItemBg)
//                .load(data.image)
//                .into(binding.ivItemBg)
            binding.ivItemBg.setBackgroundResource(data.image)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnSplashViewHolder {
        val view= ItemUnsplashBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return UnSplashViewHolder(view)
    }

    override fun onBindViewHolder(holder: UnSplashViewHolder, position: Int) {
        holder.bind(listUnSplash!![position])
    }


    override fun getItemCount(): Int {
        return listUnSplash!!.size
    }
}