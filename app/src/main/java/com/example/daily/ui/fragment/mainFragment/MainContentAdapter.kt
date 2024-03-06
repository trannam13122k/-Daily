package com.example.daily.ui.fragment.mainFragment

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.daily.R
import com.example.daily.model.Content

class MainContentAdapter() : RecyclerView.Adapter<MainContentAdapter.MainContentViewHolder>() {

    private var listUser: List<Content>? = arrayListOf()

    fun setData(listContent: List<Content>) {
        this.listUser = listContent
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainContentViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
        return MainContentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser!!.size
    }

    override fun onBindViewHolder(holder: MainContentViewHolder, position: Int) {
        val content = listUser!!.get(position)

        holder.tv_content.setText(content.content)

        holder.itemView.setOnClickListener {

        }

        holder.image.setOnClickListener {

        }

    }

    inner class MainContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_content: TextView
        var image : ImageView
        init {
            tv_content = itemView.findViewById(R.id.tv_content)
            image= itemView.findViewById(R.id.imgFavourite)
        }
    }
}