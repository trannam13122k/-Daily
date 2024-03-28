package com.example.daily.ui.fragment.themes.edit.backGroundEditing.unsplash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemUnspashBinding
import com.example.daily.ui.fragment.themes.edit.backGroundEditing.colorEdittingBG.ColorsBG

class UnSplashAdapter(private val listUnSplash: List<UnsplashModel>?) :
    RecyclerView.Adapter<UnSplashAdapter.UnSplashViewHolder>() {

    var onClickItem: ((UnsplashModel) -> Unit)? = null

    inner class UnSplashViewHolder(val binding: ItemUnspashBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UnsplashModel) {
            binding.ivItemBg.setBackgroundResource(data.image)

            binding.root.setOnClickListener {
                onClickItem?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnSplashViewHolder {
        val view = ItemUnspashBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UnSplashViewHolder(view)
    }

    override fun onBindViewHolder(holder: UnSplashViewHolder, position: Int) {
        holder.bind(listUnSplash!![position])
    }


    override fun getItemCount(): Int {
        return listUnSplash!!.size
    }
}