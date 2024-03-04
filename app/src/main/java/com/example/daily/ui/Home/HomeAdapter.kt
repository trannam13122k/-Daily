package com.example.daily.ui.Home

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemContentBinding


class HomeAdapter (
    private val listContent : List<ContentModel>?,
    private val textColor: String,
//    private val text_Font :Int
): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){
    inner class HomeViewHolder(val binding: ItemContentBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data :ContentModel){
            binding.content.text=data.content
            if (textColor != null) {
                binding.content.setTextColor(Color.parseColor(textColor))
            }
//            val context = binding.root.context
//            binding.content.typeface = ResourcesCompat.getFont(context, text_Font)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val view= ItemContentBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        holder.bind(listContent!![position])
        Log.d("TextColor", "Màu: $textColor")
    }

    override fun getItemCount(): Int {
        return listContent!!.size
    }
}