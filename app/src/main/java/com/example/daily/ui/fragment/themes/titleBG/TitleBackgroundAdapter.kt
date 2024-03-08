package com.example.daily.ui.fragment.themes.titleBG

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.R
import com.example.daily.databinding.ItemTitleThemesBinding

class TitleBackgroundAdapter( private val listTitle: List<TitleBackground>) : RecyclerView.Adapter<TitleBackgroundAdapter.TitleBackgroundViewHolder>() {

    private var selectedItemPosition = 0
    var onClickItem: ((TitleBackground) -> Unit)? = null

    inner class TitleBackgroundViewHolder(val binding : ItemTitleThemesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(title : TitleBackground,position: Int){
            binding.tvTitle.text= title.title

            val isSelected = position == selectedItemPosition

            val backgroundColor = if (isSelected) R.color.gray else R.color.white
            val textColor = if (isSelected) R.color.white else R.color.gray
            binding.llBg.setBackgroundColor(binding.root.context.getColor(backgroundColor))
            binding.tvTitle.setTextColor(binding.root.context.getColor(textColor))

            binding.root.setOnClickListener {
                val previousSelectedItemPosition = selectedItemPosition
                selectedItemPosition= adapterPosition
                notifyItemChanged(previousSelectedItemPosition)
                notifyItemChanged(selectedItemPosition)
                onClickItem?.invoke(title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleBackgroundViewHolder {
        val binding  =ItemTitleThemesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TitleBackgroundViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTitle.size
    }

    override fun onBindViewHolder(holder: TitleBackgroundViewHolder, position: Int) {
        holder.bind(listTitle[position],position)
    }

}