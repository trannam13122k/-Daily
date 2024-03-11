package com.example.daily.ui.fragment.edit.textEditing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemEditBinding

class TextEditingAdapter(private val listText: List<TextEdit>?) :
    RecyclerView.Adapter<TextEditingAdapter.TextEditingViewHolder>() {

    var onClickItem: ((TextEdit) -> Unit)? = null

    inner class TextEditingViewHolder(private val binding: ItemEditBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(textEdit: TextEdit) {
            binding.ivIcon.post {
                binding.ivIcon.setImageResource(textEdit.icon)
            }

            binding.root.setOnClickListener {
                onClickItem?.invoke(textEdit)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextEditingViewHolder {
        val binding = ItemEditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextEditingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listText?.size ?: 0
    }

    override fun onBindViewHolder(holder: TextEditingViewHolder, position: Int) {
       holder.bind(listText!![position])
    }

}