package com.example.daily.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.daily.R

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB

    abstract fun getViewBinding(inflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding(layoutInflater)
        setContentView(binding.root)

        init()
        setUpView()
    }

    abstract fun init()
    abstract fun setUpView()

    @Throws
    open fun openFragment(
        fragmentClazz: Class<*>,
        args: Bundle? = null,
        addBackStack: Boolean = false
    ) {
        val tag = fragmentClazz.simpleName
        try {
            val fragment: Fragment
            try {
                fragment = (fragmentClazz.asSubclass(Fragment::class.java)).newInstance()
                    .apply { arguments = args }

                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                if (addBackStack) {
                    transaction.addToBackStack(tag)
                }
                transaction.replace(R.id.fragment_container_viewMain, fragment, tag)
                transaction.commit()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    companion object{
        var onBackPress : (()-> Unit)? = null
    }

}