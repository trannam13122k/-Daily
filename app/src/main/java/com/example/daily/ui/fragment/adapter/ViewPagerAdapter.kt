package com.example.daily.ui.fragment.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.daily.ui.fragment.content.Content
import com.example.daily.ui.fragment.content.ContentFragment

class ViewPagerAdapter(
    fm: FragmentManager,
    behavior: Int,
    private val mlistContent: List<Content>
) : FragmentStatePagerAdapter(fm, behavior) {

    override fun getCount(): Int {
        return mlistContent.size
    }

    override fun getItem(position: Int): Fragment {
        val content: Content = mlistContent.get(position)
        val contentFragment = ContentFragment()
        val bundle = Bundle().apply {
            putString("fragment", content.toString())
        }
        contentFragment.arguments= bundle

        return contentFragment
    }
}


