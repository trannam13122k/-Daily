package com.example.daily.ui.fragment.edit.textEditing.textEffect.pickerItem

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemColorPickerBinding


class ItemPickerAdapter (private val context: Context, private var dataList: List<ItemPickerModel>, private val recyclerView: RecyclerView?) :
    RecyclerView.Adapter<ItemPickerAdapter.TextViewHolder>() {

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

    fun swapData(newData: List<ItemPickerModel>) {
        dataList = newData
        notifyDataSetChanged()
    }

    inner class TextViewHolder(private val binding: ItemColorPickerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemPickerModel) {
            binding.ivIcon.setImageResource(item.icon)
        }
    }
}