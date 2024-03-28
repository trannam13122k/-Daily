package com.example.daily.ui.fragment.settingDaiLy.setting.general.itemFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.FragmentManager
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentContactBinding
import com.example.daily.databinding.FragmentPrivacyBinding

class PrivacyFragment : BaseFragment<FragmentPrivacyBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPrivacyBinding {
        return FragmentPrivacyBinding.inflate(inflater)
    }

    override fun init() {
        // do nothing
    }

    override fun setUpView() {
        clickListener()
    }

    private fun clickListener() {
        val privacyPolicyText: String = resources.getString(R.string.privacy_policy_text)
        val formattedText = HtmlCompat.fromHtml(privacyPolicyText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvPrivacyPolicy.text = formattedText
    }



}