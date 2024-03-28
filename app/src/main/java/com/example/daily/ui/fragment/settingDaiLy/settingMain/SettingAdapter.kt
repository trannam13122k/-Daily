package com.example.daily.ui.fragment.settingDaiLy.settingMain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemSettingLayoutBinding

class SettingAdapter(private val listSetting: List<SettingModel>) :
    RecyclerView.Adapter<SettingAdapter.SettingViewHolder>() {

    var onClickItem: ((SettingModel) -> Unit)? = null

    inner class SettingViewHolder(val binding: ItemSettingLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(setting: SettingModel) {
            with(binding) {
                tvTitle.text = setting.title
                ivIcon.setImageResource(setting.icon)

                root.setOnClickListener {
                    onClickItem?.invoke(setting)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val view =
            ItemSettingLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SettingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listSetting.size
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.bind(listSetting!![position])
    }

}