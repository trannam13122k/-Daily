package com.example.daily.ui.fragment.edit

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentEditBinding
import com.example.daily.ui.fragment.edit.backGroundEditing.colorEdittingBG.ColorAdapter
import com.example.daily.ui.fragment.edit.backGroundEditing.colorEdittingBG.ColorsBG
import com.example.daily.ui.fragment.edit.backGroundEditing.unsplash.UnSplashFragment
import com.example.daily.util.PickerLayoutManager
import com.example.daily.util.Utils
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EditFragment : BaseFragment<FragmentEditBinding>() {


    private lateinit var colorAdapter: ColorAdapter

    var listDrawableColors: List<ColorsBG>? = null
    private val snapHelper: SnapHelper = LinearSnapHelper()

    private  val launcher: ActivityResultLauncher<PickVisualMediaRequest>  =registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.ivBg?.setImageURI(uri)
        } else {
            Toast.makeText(requireContext(), "No say", Toast.LENGTH_SHORT).show()
        }
    }
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentEditBinding {
        return FragmentEditBinding.inflate(inflater)
    }

    override fun init() {
        clickListener()
    }

    override fun setUpView() {
        setUpTapLayoutMain()
        tabLayoutEditing()
    }

    //permissions
    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else {
            requestStoragePermission()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Utils.PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                Toast.makeText(
                    requireContext(), "Storage permission is required...", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            Utils.PERMISSION_REQUEST_CODE
        )
    }

    private fun clickListener() {
        binding.tvCancel.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.tvSave.setOnClickListener {
            Toast.makeText(requireContext(), "Save", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpTapLayoutMain() {
        binding.tabLayout.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                requireContext(), R.color.gray
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
                requireContext(), R.color.gray
            )
        )

        val library = binding.tabLayoutBg.newTab()
        library.text = resources.getString(R.string.library)
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

        binding.tabLayoutBg.getTabAt(0)?.view?.setOnClickListener {
            binding.rvColorBg.visibility = View.GONE
            checkPermissions()
            binding.ivBg?.setImageURI(null)
        }

        binding.tabLayoutBg.getTabAt(1)?.view?.setOnClickListener {
            binding.rvColorBg.visibility = View.GONE
            openFragment(UnSplashFragment::class.java, null, true)
        }

        binding.tabLayoutBg.getTabAt(2)?.view?.setOnClickListener {
            colorEditing()
        }
    }

    private fun colorEditing() {
        listDrawableColors = listOf(
            R.drawable.color_one,
            R.drawable.color_two,
            R.drawable.color_three,
            R.drawable.color_four,
            R.drawable.color_five,
            R.drawable.color_six,
            R.drawable.color_seven,
            R.drawable.color_eight,
            R.drawable.color_nine,
            R.drawable.color_ten,
            R.drawable.color_eleven,
            R.drawable.color_twelve,
            R.drawable.color_thirteen,
            R.drawable.color_fourteen,
            R.drawable.color_fifteen,
            R.drawable.color_sixteen,
            R.drawable.color_seventeen
        ).map { ColorsBG(it) }

        val colorList = listOf(
            R.color.color_one,
            R.color.color_two,
            R.color.color_three,
            R.color.color_four,
            R.color.color_five,
            R.color.color_six,
            R.color.color_seven,
            R.color.color_eight,
            R.color.color_nine,
            R.color.color_ten,
            R.color.color_eleven,
            R.color.color_twelve,
            R.color.color_thirteen,
            R.color.color_fourteen,
            R.color.color_fifteen,
            R.color.color_sixteen,
            R.color.color_seventeen
        )

        binding.relativeLayout.visibility = View.GONE
        binding.ivBg.setImageURI(null)
        binding.rvColorBg.visibility = View.VISIBLE

        val pickerLayoutManager = PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)
        pickerLayoutManager.changeAlpha = true
        pickerLayoutManager.scaleDownBy = 0.99f
        pickerLayoutManager.scaleDownDistance = 0.8f

        colorAdapter = ColorAdapter(listDrawableColors)
        snapHelper.attachToRecyclerView(binding.rvEditText)
        binding.rvColorBg.layoutManager = pickerLayoutManager

        binding.rvColorBg.apply {
            adapter = colorAdapter
        }

        pickerLayoutManager.setOnScrollStopListener { view ->
            val position = binding.rvColorBg.getChildAdapterPosition(view)

            if (position in 0 until colorList.size) {
                binding.ivBg.setBackgroundResource(colorList[position])
            } else {
                Toast.makeText(requireContext(), "Please Select The Image ", Toast.LENGTH_SHORT).show()
            }
        }
    }

  //Text Editting

}


