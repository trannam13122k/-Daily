package com.example.daily.ui.fragment.settingDaiLy.affirmations.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.databinding.ItemCollectionsBinding
import com.example.daily.model.CollectionModel


class CollectionsAdapter(private var listCollections: List<CollectionModel>?) :
    RecyclerView.Adapter<CollectionsAdapter.CollectionsViewHolder>() {
    var onClickItem: ((CollectionModel) -> Unit)? = null

    inner class CollectionsViewHolder(val binding: ItemCollectionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CollectionModel) {
            binding.tvName.text = data.nameCollection
            binding.root.setOnClickListener {
                onClickItem?.invoke(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionsAdapter.CollectionsViewHolder {
        val view =
            ItemCollectionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        holder.bind(listCollections!![position])
    }

    override fun getItemCount(): Int {
        return listCollections!!.size
    }

    fun setData(newList: List<CollectionModel>?) {
        listCollections = newList
        notifyDataSetChanged()
    }
}