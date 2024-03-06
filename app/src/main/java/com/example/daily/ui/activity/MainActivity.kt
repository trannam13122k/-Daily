package com.example.daily.ui.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.example.daily.R
import com.example.daily.base.BaseActivity
import com.example.daily.databinding.ActivityMainBinding
import com.example.daily.ui.fragment.mainFragment.MainFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var mainFragment: MainFragment

    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun init() {
        mainFragment = MainFragment()
        openFragment(mainFragment::class.java, null, false)

    }

    override fun setUpView() {

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_viewMain)
        when (fragment) {
            is MainFragment -> {
                fragment.onBackPressed()
            }

            else-> super.onBackPressed()
        }
    }
}

