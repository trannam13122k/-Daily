package com.example.daily.ui.fragment.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.daily.ui.fragment.mainFragment.content.Content
import com.example.daily.ui.fragment.mainFragment.content.ContentFragment

class ViewPager2Adapter(fragmentActivity: FragmentActivity, private val mListquestion: List<Content>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        val question = mListquestion[position]

        val questionFragment = ContentFragment()
        val bundle = Bundle()
        bundle.putSerializable("question_object", question)

        questionFragment.arguments = bundle
        return questionFragment
    }

    override fun getItemCount(): Int {
        return mListquestion.size
    }
}



