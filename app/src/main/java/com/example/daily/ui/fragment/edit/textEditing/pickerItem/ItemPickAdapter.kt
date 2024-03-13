package com.example.daily.ui.fragment.edit.textEditing.pickerItem

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemColorPickerBinding


class ItemPickAdapter (private val context: Context, private var dataList: List<ItemColorModel>, private val recyclerView: RecyclerView?) :
    RecyclerView.Adapter<ItemPickAdapter.TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemColorPickerBinding.inflate(inflater, parent, false)
        return TextViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            recyclerView?.smoothScrollToPosition(position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun swapData(newData: List<ItemColorModel>) {
        dataList = newData
        notifyDataSetChanged()
    }

    inner class TextViewHolder(private val binding: ItemColorPickerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemColorModel) {
            binding.ivIcon.setImageResource(item.icon)
        }
    }
}