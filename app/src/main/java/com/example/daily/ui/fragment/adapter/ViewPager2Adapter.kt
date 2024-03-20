package com.example.daily.ui.fragment.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.daily.ui.fragment.categories.model.Content
import com.example.daily.ui.fragment.mainFragment.contentMain.ContentMainFragment
import com.example.daily.ui.fragment.mainFragment.contentMain.ContentTest

class ViewPager2Adapter(fragmentActivity: FragmentActivity, private val mListquestion: List<ContentTest>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        val question = mListquestion[position]

        val questionFragment = ContentMainFragment()

        val bundle = Bundle()
        bundle.putSerializable("question_object", question)

        questionFragment.arguments = bundle
        return questionFragment
    }

    override fun getItemCount(): Int {
        return mListquestion.size
    }
}

//class ViewPager2Adapter(fragmentActivity: FragmentActivity, private val mListquestion: List<Content>) :
//    FragmentStateAdapter(fragmentActivity) {
//
//    override fun createFragment(position: Int): Fragment {
//        val question = mListquestion[position]
//
//        val questionFragment = ContentMainFragment()
//        val bundle = Bundle()
//        bundle.putSerializable("question_object", question)
//
//        questionFragment.arguments = bundle
//        return questionFragment
//    }
//
//    override fun getItemCount(): Int {
//        return mListquestion.size
//    }
//}


//class ViewPager2Adapter(
//    private val fragmentActivity: FragmentActivity,
//    private val listContent: List<String>,
//    private val textColor: String,
//    private val text_Font: Int
//) :
//    FragmentStateAdapter(fragmentActivity) {
//
//    override fun createFragment(position: Int): Fragment {
//        val content = listContent[position]
//
//        val questionFragment = ContentFragment()
//        val bundle = Bundle()
//        bundle.putString("content", content)
//        bundle.putString("textColor", textColor)
//        bundle.putInt("text_Font", text_Font)
//        questionFragment.arguments = bundle
//        return questionFragment
//    }
//
//    override fun getItemCount(): Int {
//        return listContent.size
//    }
//}