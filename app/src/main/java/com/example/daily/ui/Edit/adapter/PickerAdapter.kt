package com.example.daily.ui.Edit.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemEditTextBinding
import com.example.daily.ui.Edit.model.PickerItem


class PickerAdapter(private val context: Context, private var dataList: List<PickerItem>, private val recyclerView: RecyclerView?) :
    RecyclerView.Adapter<PickerAdapter.TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemEditTextBinding.inflate(inflater, parent, false)
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

    fun swapData(newData: List<PickerItem>) {
        dataList = newData
        notifyDataSetChanged()
    }

    inner class TextViewHolder(private val binding: ItemEditTextBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PickerItem) {
            binding.ivIcon.setImageResource(item.icon)
        }
    }
}