package com.example.daily.ui.fragment.edit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentEditBinding
import com.google.android.material.tabs.TabLayout

class EditFragment : BaseFragment<FragmentEditBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditBinding {
        return FragmentEditBinding.inflate(inflater)
    }

    override fun init() {
        setUpTapLayout()
    }

    override fun setUpView() {

    }


    private fun setUpTapLayout() {
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.gray))

        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.icon_bg_edit))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.icon_text_lock))


         binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.ivName.text = getString(R.string.background_edit)
                        binding.tabLayoutBg.visibility = View.VISIBLE
                        binding.relativeLayout.visibility = View.GONE
                        binding.rvColorBg.visibility= View.GONE
                    }
                    1 -> {
                        binding.ivName.text = getString(R.string.text_editing)
                        binding.tabLayoutBg.visibility = View.GONE
                        binding.relativeLayout.visibility = View.VISIBLE
                        binding.rvColorBg.visibility= View.GONE
                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


    }

}