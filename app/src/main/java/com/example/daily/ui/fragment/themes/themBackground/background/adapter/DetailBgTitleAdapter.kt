package com.example.daily.ui.fragment.themes.themBackground.background.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.daily.R
import com.example.daily.databinding.ItemThemesBgBinding
import com.example.daily.ui.fragment.themes.themBackground.background.model.ThemesModel


class DetailBgTitleAdapter(private var listThemes: List<ThemesModel>?) :
    RecyclerView.Adapter<DetailBgTitleAdapter.DetailBgTitleViewHolder>() {
    var onClickViewAllItem: ((ThemesModel) -> Unit)? = null
    var onClickItem: ((ThemesModel) -> Unit)? = null

    inner class DetailBgTitleViewHolder(val binding: ItemThemesBgBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ThemesModel) {
            with(binding) {
                Glide.with(ivBg.context).load(data.image).into(ivBg)
                llViewAll.setOnClickListener {
                    onClickViewAllItem?.invoke(data)
                }

                root.setOnClickListener {
                    onClickItem?.invoke(data)
                }

                ivKey.setImageResource(
                    if (data.check) R.drawable.icon_key else {
                        ivKey.visibility = View.GONE
                        0
                    }
                )

                llViewAll.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailBgTitleViewHolder {
        val view = ItemThemesBgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailBgTitleViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailBgTitleViewHolder, position: Int) {
        holder.bind(listThemes!![position])
    }

    override fun getItemCount(): Int {
        return listThemes!!.size
    }

}