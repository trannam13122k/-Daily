package com.example.daily.ui.fragment.edit

import android.Manifest
import android.content.pm.PackageManager
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
import com.example.daily.databinding.FragmentEditBinding
import com.example.daily.ui.fragment.edit.backGroundEditing.colorEdittingBG.ColorAdapter
import com.example.daily.ui.fragment.edit.backGroundEditing.unsplash.UnSplashFragment
import com.example.daily.ui.fragment.edit.textEditing.TextEditingAdapter
import com.example.daily.ui.fragment.edit.textEditing.font.FontAdapter
import com.example.daily.util.DataB
import com.example.daily.util.PickerLayoutManager
import com.example.daily.util.Utils
import com.google.android.material.tabs.TabLayout
import kotlin.math.log

class EditFragment : BaseFragment<FragmentEditBinding>() {

    private lateinit var colorAdapter: ColorAdapter
    private lateinit var textEditingAdapter: TextEditingAdapter
    private lateinit var fontAdapter: FontAdapter

    private var type: String = "Color"

    private val snapHelper: SnapHelper = LinearSnapHelper()

    private val launcher: ActivityResultLauncher<PickVisualMediaRequest> =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.ivBg?.setImageURI(uri)
            } else {
                Toast.makeText(requireContext(), "No Load Image", Toast.LENGTH_SHORT).show()
            }
        }

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
        setUpTapLayoutMain()
        tabLayoutBackGroundEditing()
        setUpTextEditing()
        colorEditing()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Utils.PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                Toast.makeText(
                    requireContext(),
                    "Storage permission is required...",
                    Toast.LENGTH_SHORT
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
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }


        binding.tvSave.setOnClickListener {
            Toast.makeText(requireContext(), "Save", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpTapLayoutMain() {
        binding.rcvItem.visibility = View.GONE
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.icon_bg_edit))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setIcon(R.drawable.icon_text_lock))

        binding.tabLayout.setSelectedTabIndicatorColor(
            ContextCompat.getColor(requireContext(), R.color.gray)
        )

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.ivName.text = getString(R.string.background_edit)
                        binding.tabLayoutBg.visibility = View.VISIBLE
                        binding.relativeLayout.visibility = View.GONE
                    }

                    1 -> {
                        binding.ivName.text = getString(R.string.text_editing)
                        binding.tabLayoutBg.visibility = View.GONE
                        binding.relativeLayout.visibility = View.VISIBLE
                        binding.rcvItem.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    // backGround Editting...........................................................................
    private fun tabLayoutBackGroundEditing() {

        val color = binding.tabLayoutBg.newTab()
        val library = binding.tabLayoutBg.newTab()
        val unSplash = binding.tabLayoutBg.newTab()

        library.text = resources.getString(R.string.library)
        unSplash.text = "Unsplash"
        color.text = "Color"

        library.setIcon(R.drawable.icon_libary)
        unSplash.setIcon(R.drawable.icon_unsplash)
        color.setIcon(R.drawable.icon_color)

        binding.tabLayoutBg.setSelectedTabIndicatorColor(
            ContextCompat.getColor(requireContext(), R.color.gray)
        )
        binding.tabLayoutBg.addTab(library)
        binding.tabLayoutBg.addTab(unSplash)
        binding.tabLayoutBg.addTab(color)

        binding.tabLayoutBg.getTabAt(0)?.view?.setOnClickListener {
            binding.rcvItem.visibility = View.GONE
            checkPermissions()
            binding.ivBg?.setImageURI(null)
        }
        binding.tabLayoutBg.getTabAt(1)?.view?.setOnClickListener {
            binding.rcvItem.visibility = View.GONE
            openFragment(UnSplashFragment::class.java, null, true)
        }
        binding.tabLayoutBg.getTabAt(2)?.view?.setOnClickListener {
            binding.rcvItem.visibility = View.VISIBLE

        }
    }

    private fun colorEditing() {
        binding.relativeLayout.visibility = View.GONE
        binding.ivBg.setImageURI(null)

        val pickerLayoutManager =
            PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)
        colorAdapter = ColorAdapter(DataB.listDrawableColors)
        snapHelper.attachToRecyclerView(binding.rvEditText)
        pickerLayoutManager.apply {
            changeAlpha = true
            scaleDownBy = 0.99f
            scaleDownDistance = 0.8f
        }
        binding.rcvItem.layoutManager = pickerLayoutManager
        binding.rcvItem.adapter = colorAdapter

        pickerLayoutManager.setOnScrollStopListener { view ->
            val position = binding.rcvItem.getChildAdapterPosition(view)

            if (position in 0 until DataB.colorList.size) {
                setColor(type, position)
            } else {
                Toast.makeText(requireContext(), "Please Select The Image ", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun setColor(type: String, position: Int) {
        if (binding.tabLayout.getTabAt(0)?.isSelected == true) {
            binding.ivBg.setBackgroundResource(DataB.colorList.get(position))
        } else {
            when (type) {
                "Color" -> {
                    context?.getColor(DataB.colorList[position])
                        ?.let { binding.tvContent.setTextColor(it) }
                    Log.e("setColor", "setColor: ${DataB.colorList[position]}", )
                }
            }

        }
    }


    //Text Editting

    fun setUpTextEditing() {

        val pickerLayoutManager = PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)
        pickerLayoutManager.apply {
            changeAlpha = true
            scaleDownBy = 0.99f
            scaleDownDistance = 0.8f
        }
        snapHelper.attachToRecyclerView(binding.rvEditText)
        binding.rvEditText.layoutManager = pickerLayoutManager

        textEditingAdapter = TextEditingAdapter(DataB.listTextEditing)
        binding.rvEditText.adapter = textEditingAdapter

        pickerLayoutManager.setOnScrollStopListener { view ->
            val position = binding.rvEditText.getChildAdapterPosition(view)
            val selectedItem = DataB.listTextEditing?.get(position)
            binding.tvNameEdit.text = selectedItem?.text
            when (selectedItem?.text) {
                "Color" -> {
                    type = "Color"
                    binding.rcvItem.visibility = View.VISIBLE
                }

                "Font" -> {
                    binding.rcvItem.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Font", Toast.LENGTH_SHORT).show()

                    fontAdapter = FontAdapter(DataB.listFontEditing)
                    fontAdapter.notifyDataSetChanged()
                    binding.rcvItem.adapter = fontAdapter

                }

                "Size" -> {
                    Toast.makeText(requireContext(), "Size", Toast.LENGTH_SHORT).show()

                }

                "Alignment" -> {
                    Toast.makeText(requireContext(), "Alignment", Toast.LENGTH_SHORT).show()

                }

                "Case" -> {
                    Toast.makeText(requireContext(), "Case", Toast.LENGTH_SHORT).show()

                }

                "Shadow" -> {
                    Toast.makeText(requireContext(), "Shadow", Toast.LENGTH_SHORT).show()

                }

                "Stroke" -> {
                    Toast.makeText(requireContext(), "Stroke", Toast.LENGTH_SHORT).show()

                }
            }
            Log.d("selectedItem", "onCreate: ${selectedItem?.text}")
        }
    }

}


