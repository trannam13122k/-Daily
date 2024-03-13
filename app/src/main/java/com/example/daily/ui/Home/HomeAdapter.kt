package com.example.daily.ui.Home

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.R
import com.example.daily.databinding.ItemContentBinding
import com.example.daily.ui.Categories.Model.ContentModelFireBase


class HomeAdapter (
    private val listContent : List<String>?,
    private val textColor: String,
//    private val text_Font :Int
): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){
    var onClickFavourite: ((String) -> Unit)? = null
    var isFavourite = true
    inner class HomeViewHolder(val binding: ItemContentBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data :String){
            binding.content.text=data
            if (textColor != null) {
                binding.content.setTextColor(Color.parseColor(textColor))
            }

            binding.ivFavourite.setOnClickListener {
                isFavourite=!isFavourite
                binding.ivFavourite.setImageResource(if(isFavourite) R.drawable.favourite else R.drawable.favourite_true)
                onClickFavourite?.invoke(data)
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