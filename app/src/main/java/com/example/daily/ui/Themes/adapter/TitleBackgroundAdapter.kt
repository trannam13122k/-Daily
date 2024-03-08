package com.example.daily.ui.Themes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.R
import com.example.daily.databinding.ItemTitileThemesBinding
import com.example.daily.ui.Themes.Model.TitleBackgroundModel

class TitleBackgroundAdapter(private val listTitle: List<TitleBackgroundModel>) :
    RecyclerView.Adapter<TitleBackgroundAdapter.TitleBackgroundViewHolder>() {
    private var selectedItemPosition = 0
    var onClickItem: ((TitleBackgroundModel) -> Unit)? = null
    var isCheck = true
    
    inner class TitleBackgroundViewHolder(val binding: ItemTitileThemesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TitleBackgroundModel, position: Int) {
            binding.tvTitle.text = data.title
            if (position == selectedItemPosition) {
                binding.llBg.setBackgroundColor(binding.root.resources.getColor(R.color.gray))
                binding.tvTitle.setTextColor(binding.root.resources.getColor(R.color.white))
            } else {
                binding.llBg.setBackgroundColor(binding.root.resources.getColor(R.color.white))
                binding.tvTitle.setTextColor(binding.root.resources.getColor(R.color.gray))
            }

            binding.root.setOnClickListener {

                // Cập nhật vị trí item được chọn
                val previousSelectedItemPosition = selectedItemPosition
                selectedItemPosition = adapterPosition
                // Cập nhật lại giao diện
                notifyItemChanged(previousSelectedItemPosition)
                notifyItemChanged(selectedItemPosition)
                onClickItem?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleBackgroundViewHolder {
        val view =
            ItemTitileThemesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleBackgroundViewHolder(view)
    }

    override fun onBindViewHolder(holder: TitleBackgroundViewHolder, position: Int) {
        holder.bind(listTitle[position], position)
    }

    override fun getItemCount(): Int {
        return listTitle.size
    }
}