package com.example.daily.ui.fragment.edit

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentEditBinding
import com.example.daily.ui.fragment.edit.unsplash.UnSplashFragment
import com.example.daily.util.Utils
import com.google.android.material.tabs.TabLayout

class EditFragment : BaseFragment<FragmentEditBinding>() {

    private lateinit var launcher: ActivityResultLauncher<PickVisualMediaRequest>

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditBinding {
        return FragmentEditBinding.inflate(inflater)
    }

    override fun init() {
        clickListener()
    }

    override fun setUpView() {
        setUpTapLayout()
        tabLayoutEditing()

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.ivBg?.setImageURI(uri)
            } else {
                Toast.makeText(requireContext(), "1212313232 - no chekc ", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun clickListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }


    private fun setUpTapLayout() {
        binding.tabLayout.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.gray
            )
        )

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
                        binding.rvColorBg.visibility = View.GONE

                    }

                    1 -> {
                        binding.ivName.text = getString(R.string.text_editing)
                        binding.tabLayoutBg.visibility = View.GONE
                        binding.relativeLayout.visibility = View.VISIBLE
                        binding.rvColorBg.visibility = View.GONE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


    }

    // backGround Editting...........................................................................
    private fun tabLayoutEditing() {
        binding.tabLayoutBg.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.gray
            )
        )

        val library = binding.tabLayoutBg.newTab()
        library.text = "Library"
        library.setIcon(R.drawable.icon_libary)

        val unSplash = binding.tabLayoutBg.newTab()
        unSplash.text = "Unsplash"
        unSplash.setIcon(R.drawable.icon_unsplash)

        val color = binding.tabLayoutBg.newTab()
        color.text = "Color"
        color.setIcon(R.drawable.icon_color)

        binding.tabLayoutBg.addTab(library)
        binding.tabLayoutBg.addTab(unSplash)
        binding.tabLayoutBg.addTab(color)

        binding.tabLayoutBg.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {

                    0 -> {
                        clickLibrary()
                    }

                    1 -> {
                        clickUnsplash()
                    }

                    2 -> {
                        clickColor()
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun clickLibrary(){
        binding.tabLayoutBg.getTabAt(0)?.view?.setOnClickListener {

            binding.ivBg?.setImageURI(null)

            binding.rvColorBg.visibility = View.GONE
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        }
    }

    private fun clickUnsplash() {
        binding.tabLayoutBg.getTabAt(1)?.view?.setOnClickListener {
            binding.rvColorBg.visibility = View.GONE
           openFragment(UnSplashFragment::class.java,null,true)
        }
    }

    private fun clickColor() {
        binding.tabLayoutBg.getTabAt(2)?.view?.setOnClickListener {
            binding.rvColorBg.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "Color", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Utils.PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Storage Ok", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Storage permission is required...", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun loadImageFromSharedPreferences() {

    }

}