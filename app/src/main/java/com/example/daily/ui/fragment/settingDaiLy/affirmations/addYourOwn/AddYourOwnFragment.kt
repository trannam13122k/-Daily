package com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn


import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentAddYourOwnBinding


class AddYourOwnFragment : BaseFragment<FragmentAddYourOwnBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddYourOwnBinding {
        return FragmentAddYourOwnBinding.inflate(inflater)
    }

    override fun init() {

    }

    override fun setUpView() {
        listenerClick()
    }
    private fun listenerClick() {
        binding.ivClose.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}