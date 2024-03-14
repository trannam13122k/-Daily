package com.example.daily.ui.fragment.edit

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentEditBinding
import com.example.daily.ui.fragment.edit.backGroundEditing.colorEdittingBG.ColorAdapter
import com.example.daily.ui.fragment.edit.backGroundEditing.unsplash.UnSplashFragment
import com.example.daily.ui.fragment.edit.textEditing.textEffect.PickerItemAdapter
import com.example.daily.ui.fragment.edit.textEditing.textEffect.pickerItem.ItemPickerAdapter
import com.example.daily.ui.fragment.edit.textEditing.textEffect.pickerItem.ItemPickerModel
import com.example.daily.util.DataB
import com.example.daily.util.PickerLayoutManager
import com.example.daily.util.Utils
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class EditFragment : BaseFragment<FragmentEditBinding>() {

    private lateinit var colorAdapter: ColorAdapter
    private lateinit var pickerItemAdapter: PickerItemAdapter
    private lateinit var adapterItem: ItemPickerAdapter

    private var type: String = "Color"

    private val snapHelper: SnapHelper = LinearSnapHelper()

    private lateinit var preferences: Preferences
    private lateinit var launcher: ActivityResultLauncher<PickVisualMediaRequest>

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditBinding {
        return FragmentEditBinding.inflate(inflater)
    }

    override fun init() {
        preferences = Preferences.getInstance(requireContext())

        clickListener()
    }

    override fun setUpView() {
        setUpTapLayoutMain()
        tabLayoutBackGroundEditing()
        setUpTextEditing()
        colorEditing()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.ivBg?.setImageURI(uri)
                saveImageUriToSharedPreferences(uri.toString())

            } else {
                Toast.makeText(requireContext(), "No Load Image", Toast.LENGTH_SHORT).show()
                loadImageFromSharedPreferences()
            }
        }
    }

    private fun loadImageFromSharedPreferences() {
        CoroutineScope(Dispatchers.IO).launch {
            val imageUri = preferences.getString("imageBg")
            if (!imageUri.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    binding.ivBg?.setImageURI(Uri.parse(imageUri))
                }
            }
        }
    }

    private fun saveImageUriToSharedPreferences(imageUri: String) {
        preferences.setString("imageBg", imageUri)
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

                loadImageFromSharedPreferences()
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
                        binding.rcvItem.visibility = View.GONE
                    }

                    1 -> {
                        binding.ivName.text = getString(R.string.text_editing)
                        binding.tabLayoutBg.visibility = View.GONE
                        binding.relativeLayout.visibility = View.VISIBLE

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

        binding.tabLayoutBg.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.gray))
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
            binding.ivBg?.setImageURI(null)
            binding.rcvItem.visibility = View.VISIBLE
            colorAdapter.notifyDataSetChanged()
            binding.rcvItem.adapter = colorAdapter

        }
    }

    private fun colorEditing() {
        binding.relativeLayout.visibility = View.GONE

        val pickerLayoutManager = PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)

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
                setEffect(type, position)
            } else {
                Toast.makeText(requireContext(), "Please Select The Image ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setEffect(type: String, position: Int) {
        if (binding.tabLayout.getTabAt(0)?.isSelected == true) {
            binding.ivBg.setBackgroundResource(DataB.colorList.get(position))
        } else {
            when (type) {
                "Color" -> {
                    context?.getColor(DataB.colorList[position])
                        ?.let { binding.tvContent.setTextColor(it) }
                    Log.e("setColor", "setColor: ${DataB.colorList[position]}")

                }

                "Font" -> {
                    binding.tvContent.typeface = ResourcesCompat.getFont(requireContext(), DataB.listFont.get(position).font)
                }

                "Size" -> {
                    binding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, DataB.listSize.get(position).size.toFloat())
                }

                "Alignment" -> {
                    binding.tvContent.gravity= DataB.listAligment[position].alignment or Gravity.CENTER
                }
                "Alignment Top" -> {
                    binding.tvContent.gravity=DataB.listAligmentTop[position].alignmentTop or Gravity.CENTER
                }
                "Case" -> {
                    if (position==0){
                        binding.tvContent.text= binding.tvContent.text.toString()?.toUpperCase()
                    }else if (position==1){
                        var name = binding.tvContent.text.toString().trim()
                        var firstLetter = name.substring(0, 1)
                        val remainingLetters = name.substring(1,name.length).toLowerCase()

                        firstLetter = firstLetter.toUpperCase();
                        name = firstLetter + remainingLetters;

                        binding.tvContent.text= name

                    }else  binding.tvContent.text= binding.tvContent.text.toString()?.toLowerCase()

                }
                "Shadow" -> {

                }
                "Stroke" -> {
                }
            }
        }
    }


    fun setUpTextEditing() {
        val pickerLayoutManager =
            PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)
        pickerLayoutManager.apply {
            changeAlpha = true
            scaleDownBy = 0.99f
            scaleDownDistance = 0.8f
        }
        snapHelper.attachToRecyclerView(binding.rvEditText)
        binding.rvEditText.layoutManager = pickerLayoutManager

        pickerItemAdapter = PickerItemAdapter(requireContext(), DataB.listTextEditings!!, binding.rvEditText)
        binding.rvEditText.adapter = pickerItemAdapter

        binding.rcvItem.visibility = View.VISIBLE

        pickerLayoutManager.setOnScrollStopListener { view ->
            val position = binding.rvEditText.getChildAdapterPosition(view)
            val selectedItem = DataB.listTextEditings?.get(position)
            binding.tvNameEdit.text = selectedItem?.text

            when (selectedItem?.text) {
                "Color" -> {
                    type = "Color"
                    setupRecyclerView(requireContext(), DataB.listColor)
                }

                "Font" -> {
                    type = "Font"
                    setupRecyclerView(requireContext(), DataB.listFont)
                }

                "Size" -> {
                    type = "Size"
                    setupRecyclerView(requireContext(), DataB.listSize)
                }

                "Alignment" -> {
                    type = "Alignment"
                    setupRecyclerView(requireContext(), DataB.listAligment)
                }

                "Alignment Top" -> {
                    type = "Alignment Top"
                    setupRecyclerView(requireContext(), DataB.listAligmentTop)
                }

                "Case" -> {
                    type = "Case"
                    setupRecyclerView(requireContext(), DataB.listCase)
                }

                "Shadow" -> {
                    type = "Shadow"
                    setupRecyclerView(requireContext(), DataB.listColor)
                }

                "Stroke" -> {
                    type = "Stroke"
                    setupRecyclerView(requireContext(), DataB.listStroke)
                }
            }
        }
    }

    // colors text ,font ,size
    fun setupRecyclerView(context: Context, itemList: List<ItemPickerModel>): RecyclerView {
        if (!::adapterItem.isInitialized) {
            adapterItem = ItemPickerAdapter(requireContext(), itemList, binding.rcvItem)
        } else {
            adapterItem.swapData(itemList)
        }

        adapterItem.notifyDataSetChanged()
        val pickerLayoutManager =
            PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)
        pickerLayoutManager.apply {
            changeAlpha = true
            scaleDownBy = 0.99f
            scaleDownDistance = 0.8f
        }

        snapHelper.attachToRecyclerView(binding.rvEditText)
        binding.rcvItem.layoutManager = pickerLayoutManager
        binding.rcvItem.adapter = adapterItem

        pickerLayoutManager.setOnScrollStopListener { view ->
            val position = binding.rcvItem.getChildAdapterPosition(view)
            setEffect(type, position)

        }
        return binding.rcvItem
    }


}


