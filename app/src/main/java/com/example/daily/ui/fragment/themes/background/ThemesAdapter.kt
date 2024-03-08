package com.example.daily.ui.fragment.themes.background

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.daily.databinding.ItemThemesBgBinding


class ThemesAdapter(private val listThemes : List<Themes>?) : RecyclerView.Adapter<ThemesAdapter.ThemesViewHolder>() {

    var onClickViewAllItem: ((Themes) -> Unit)? = null
    var onClickItem: ((Themes) -> Unit)? = null

    inner class ThemesViewHolder(val binding: ItemThemesBgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Themes) {

            binding.ivBg.post{
             binding.ivBg.setBackgroundResource(data.imageView)
            }

            binding.llViewAll.setOnClickListener {
                onClickViewAllItem?.invoke(data)
            }

            binding.root.setOnClickListener {
                onClickItem?.invoke(data)
            }

            binding.llViewAll.visibility = if (adapterPosition == 1) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemesViewHolder {
        val binding = ItemThemesBgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThemesViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return listThemes!!.size
    }

    override fun onBindViewHolder(holder: ThemesViewHolder, position: Int) {
        holder.bind(listThemes!![position])
    }
}