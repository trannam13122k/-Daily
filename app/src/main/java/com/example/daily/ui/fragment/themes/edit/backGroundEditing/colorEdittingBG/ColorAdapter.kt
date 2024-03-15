package com.example.daily.ui.fragment.themes.edit.backGroundEditing.colorEdittingBG

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemColorPickerBinding

class ColorAdapter(  private val listColor: List<ColorsBG>?) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    var onClickItem: ((ColorsBG) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val binding = ItemColorPickerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ColorViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(listColor!![position])

    }

    override fun getItemCount(): Int {
       return listColor?.size ?: 0
    }

    inner class ColorViewHolder(private val binding: ItemColorPickerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(colors: ColorsBG) {

            binding.ivIcon.post {
                binding.ivIcon.setImageResource(colors.icon)
            }

            binding.root.setOnClickListener {
                onClickItem?.invoke(colors)
            }
        }
    }
}