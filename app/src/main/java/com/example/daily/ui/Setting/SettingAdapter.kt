package com.example.daily.ui.Setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemSettingBinding
import com.example.daily.ui.Themes.Model.ThemesModel

class SettingAdapter (private val listContent : List<SettingModel>?): RecyclerView.Adapter<SettingAdapter.SettingViewHolder>(){
    var onClickItem: ((SettingModel) -> Unit)? = null
    inner class SettingViewHolder(val binding: ItemSettingBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : SettingModel){
            binding.tvTitle.text=data.title
            binding.ivIcon.setImageResource(data.icon)

            binding.root.setOnClickListener{
                onClickItem?.invoke(data)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingAdapter.SettingViewHolder {
        val view= ItemSettingBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return SettingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.bind(listContent!![position])
    }


    override fun getItemCount(): Int {
        return listContent!!.size
    }
}