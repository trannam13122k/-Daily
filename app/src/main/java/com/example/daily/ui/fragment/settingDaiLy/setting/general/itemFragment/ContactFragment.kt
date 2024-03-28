package com.example.daily.ui.fragment.settingDaiLy.setting.general.itemFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentContactBinding

class ContactFragment : BaseFragment<FragmentContactBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentContactBinding {
        return FragmentContactBinding.inflate(inflater)
    }

    override fun init() {
        // do nothing
    }

    override fun setUpView() {
        clickListener()
    }

    private fun clickListener() {
        binding.buttononeSenemail.setOnClickListener {
            val s1: String = binding.idMail.text.toString()
            val s2: String = binding.idSubject.text.toString()
            val s3: String = binding.idMessage.text.toString()

            val mail = arrayOf(s1)
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, mail)
            intent.putExtra(Intent.EXTRA_SUBJECT, s2)
            intent.putExtra(Intent.EXTRA_TEXT, s3)
            intent.setType("message/rfc822")
            startActivity(intent)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.general) {
            requireActivity().supportFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            requireActivity().onBackPressed()
            requireActivity().supportFragmentManager.popBackStack()
            return true
        }
        return super.onContextItemSelected(item)
    }

}