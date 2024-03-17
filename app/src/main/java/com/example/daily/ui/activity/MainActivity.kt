package com.example.daily.ui.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container_viewMain, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()
        }
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

