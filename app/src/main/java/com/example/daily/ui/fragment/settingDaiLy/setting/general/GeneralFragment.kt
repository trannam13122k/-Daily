package com.example.daily.ui.fragment.settingDaiLy.setting.general

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentGeneralBinding
import com.example.daily.ui.fragment.settingDaiLy.setting.general.itemFragment.ContactFragment
import com.example.daily.ui.fragment.settingDaiLy.setting.general.itemFragment.PrivacyFragment
import java.io.File


class GeneralFragment : BaseFragment<FragmentGeneralBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGeneralBinding {
        return FragmentGeneralBinding.inflate(inflater)
    }

    override fun init() {

    }

    override fun setUpView() {

        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.shareApp.setOnClickListener {
            shareApp()
        }

        binding.contactApp.setOnClickListener {
            openFragment(ContactFragment::class.java,null,true)
        }

        binding.privacyPoloicy.setOnClickListener {
            openFragment(PrivacyFragment::class.java,null,true)

        }
    }

    private fun shareApp() {
        val message = "Try This App !"

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
        }

        val shareAppIntent = Intent.createChooser(shareIntent, "Share The Application")
        if (shareAppIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(shareAppIntent)
        }
    }

}