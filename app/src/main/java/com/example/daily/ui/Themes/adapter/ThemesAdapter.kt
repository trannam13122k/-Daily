package com.example.daily.ui.Themes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.daily.R
import com.example.daily.databinding.ItemThemesBgBinding
import com.example.daily.ui.Themes.Model.ThemesModel


class ThemesAdapter (private var listThemes : List<ThemesModel>?): RecyclerView.Adapter<ThemesAdapter.HomeViewHolder>(){
    var onClickViewAllItem: ((ThemesModel) -> Unit)? = null
    var onClickItem: ((ThemesModel) -> Unit)? = null
    inner class HomeViewHolder(val binding: ItemThemesBgBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : ThemesModel){
            Glide.with(binding.ivBg.context).load(data.image).into(binding.ivBg)
            binding.llViewAll.setOnClickListener {
                onClickViewAllItem?.invoke(data)
            }
            binding.root.setOnClickListener {
                onClickItem?.invoke(data)
            }
            binding.ivKey.setImageResource(if (data.isKey) R.drawable.icon_key else {
                binding.ivKey.visibility = View.GONE
                0 })

            if(position==1){
                binding.llViewAll.visibility= View.VISIBLE
            }
            else{
                binding.llViewAll.visibility= View.GONE
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view= ItemThemesBgBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(listThemes!![position])
    }

    override fun getItemCount(): Int {
        return listThemes!!.size
    }
    fun updateData(newThemes: List<ThemesModel>) {
        listThemes = newThemes
        notifyDataSetChanged()
    }
}