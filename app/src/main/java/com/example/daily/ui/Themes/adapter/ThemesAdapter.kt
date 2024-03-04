package com.example.daily.ui.Themes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.R
import com.example.daily.databinding.ItemThemesBgBinding
import com.example.daily.ui.Themes.Model.ThemesModel


class ThemesAdapter (private val listThemes : List<ThemesModel>?): RecyclerView.Adapter<ThemesAdapter.HomeViewHolder>(){
    var onClickViewAllItem: ((ThemesModel) -> Unit)? = null
    var onClickItem: ((ThemesModel) -> Unit)? = null
    inner class HomeViewHolder(val binding: ItemThemesBgBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : ThemesModel){
            binding.ivBg.setBackgroundResource(data.imageView)
            binding.tvViewAll.text="View All"
            binding.ivKey.setBackgroundResource(R.drawable.arrow_right)
            binding.llViewAll.setOnClickListener {
                onClickViewAllItem?.invoke(data)
            }
            binding.root.setOnClickListener {
                onClickItem?.invoke(data)
            }

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
}