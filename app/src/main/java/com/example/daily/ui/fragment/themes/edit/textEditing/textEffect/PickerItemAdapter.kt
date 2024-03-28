package com.example.daily.ui.fragment.themes.edit.textEditing.textEffect

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemColorPickerBinding

class PickerItemAdapter(
    private val context: Context,
    private var dataList: List<PickerItem>,
    private val recyclerView: RecyclerView?
) :

    RecyclerView.Adapter<PickerItemAdapter.TextEditingViewHolder>() {

    inner class TextEditingViewHolder(private val binding: ItemColorPickerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PickerItem) {
            binding.ivIcon.setImageResource(item.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextEditingViewHolder {
        val binding =
            ItemColorPickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextEditingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: TextEditingViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            recyclerView?.smoothScrollToPosition(position)
        }
    }
}