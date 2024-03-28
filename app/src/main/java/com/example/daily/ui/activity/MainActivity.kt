package com.example.daily.ui.activity

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.daily.R
import com.example.daily.base.BaseActivity
import com.example.daily.databinding.ActivityMainBinding
import com.example.daily.service.NotificationService
import com.example.daily.ui.fragment.mainFragment.MainFragment
import com.example.daily.util.Utils


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun init() {
        /**do nothing**/
    }

    override fun setUpView() {
        val serviceIntent = Intent(this, NotificationService::class.java)
        this.startService(serviceIntent)
        openFragment(MainFragment()::class.java, null, false)
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