package com.example.daily.ui.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.daily.model.Content
import com.example.daily.ui.fragment.ContentFragment

class ViewPager2Adapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentList: MutableList<Fragment> = mutableListOf()

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size - 1)
    }

    fun removeFragment(position: Int) {
        fragmentList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, fragmentList.size)
    }
}




