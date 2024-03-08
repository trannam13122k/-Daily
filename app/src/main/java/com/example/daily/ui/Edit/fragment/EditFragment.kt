package com.example.daily.ui.Edit.fragment

import Preferences
import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.daily.MainActivity
import com.example.daily.PickerLayoutManager
import com.example.daily.R
import com.example.daily.databinding.FragmentEditBinding
import com.example.daily.ui.Edit.adapter.ItemPickAdapter
import com.example.daily.ui.Edit.adapter.PickerAdapter
import com.example.daily.ui.Edit.model.ItemColorModel
import com.example.daily.ui.Edit.model.PickerItem
import com.example.daily.ui.Home.HomeFragment
import com.example.notisave.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditFragment : BaseFragment<FragmentEditBinding>() {

    private lateinit var adapter: PickerAdapter

    private lateinit var adapterItem: ItemPickAdapter

    private var list: List<PickerItem>? = null
    private var itemlist: List<PickerItem>? = null
    private var listColor: List<ItemColorModel>? = null
    private var listFont: List<ItemColorModel>? = null




    private val snapHelper: SnapHelper = LinearSnapHelper()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditBinding {
        return FragmentEditBinding.inflate(inflater)
    }


    private lateinit var launcher: ActivityResultLauncher<PickVisualMediaRequest>

    private lateinit var preferences: Preferences


    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferences = Preferences.getInstance(requireContext())
        launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.ivBg?.setImageURI(uri)
                Log.d("uri", "onAttach: $uri")
                saveImageUriToSharedPreferences(uri.toString())
            } else {
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImageFromSharedPreferences()
            }
        }
    }


    override fun init() {
        database()
        loadImageFromSharedPreferences()
        setUpTabLayOut()
        setUpTabLayOutBackground()
        setUpListener()
    }

    private fun database() {
        list = listOf(
            PickerItem(R.drawable.icon_color, "Color"),
            PickerItem(R.drawable.icon_font, "Font"),
            PickerItem(R.drawable.icon_size, "Size"),
            PickerItem(R.drawable.icon_alignment, "Alignment"),
            PickerItem(R.drawable.icon_alignment_top, "Alignment"),
            PickerItem(R.drawable.icon_case, "Case"),
            PickerItem(R.drawable.icon_shadow, "Shadow"),
            PickerItem(R.drawable.icon_stroke, "Stroke")
        )
        listColor=listOf(
            ItemColorModel("#5369EC",R.drawable.color_one,"Color"),
            ItemColorModel("#4BD392",R.drawable.color_two,"Color"),
            ItemColorModel("#EC8DF4",R.drawable.color_three,"Color"),
            ItemColorModel("#FFD6B0",R.drawable.color_four,"Color"),
            ItemColorModel("#B0FAFF",R.drawable.color_five,"Color"),
            ItemColorModel("#FFB0B0",R.drawable.color_six,"Color"),
            ItemColorModel("#EA6E14",R.drawable.color_seven,"Color"),
            ItemColorModel("#2984C6",R.drawable.color_eight,"Color"),
            ItemColorModel("#3A4790",R.drawable.color_nine,"Color"),
            ItemColorModel("#225E4F",R.drawable.color_ten,"Color"),
            ItemColorModel("#C1DA28",R.drawable.color_eleven,"Color"),
            ItemColorModel("#791BB2",R.drawable.color_twelve,"Color"),
            ItemColorModel("#76412A",R.drawable.color_thirteen,"Color"),
            ItemColorModel("#EFFD53",R.drawable.color_fourteen,"Color"),
            ItemColorModel("#717797",R.drawable.color_fifteen,"Color"),
            ItemColorModel("#16D0F9",R.drawable.color_sixteen,"Color")
        )

        listFont= listOf(
//            ItemColorModel(R.font.amatic_bold,R.drawable.amatic_bold,"Font"),
//            ItemColorModel(R.font.sacramento_regular,R.drawable.sacramento,"Font"),
//            ItemColorModel(R.font.bahiana_regular,R.drawable.bahiana,"Font"),
//            ItemColorModel(R.font.bangers_regular,R.drawable.bangers,"Font"),
//            ItemColorModel(R.font.bellota_regular,R.drawable.bellota,"Font"),
//            ItemColorModel(R.font.caveat_brush_regular,R.drawable.caveat_brush,"Font"),
//            ItemColorModel(R.font.bellefair_regular,R.drawable.bellefair,"Font"),
//            ItemColorModel(R.font.coiny_regular,R.drawable.coiny,"Font"),
//            ItemColorModel(R.font.beau_rivage_regular,R.drawable.bellefair,"Font"),
        )


    }

    override fun setUpView() {
        setDataRecyclerView()

    }

    private fun setDataRecyclerView() {
        val pickerLayoutManager = PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)
        pickerLayoutManager.changeAlpha = true
        pickerLayoutManager.scaleDownBy = 0.99f
        pickerLayoutManager.scaleDownDistance = 0.8f
        adapter = PickerAdapter(requireContext(), list!!, binding.rvEditText)
        snapHelper.attachToRecyclerView(binding.rvEditText)
        binding.rvEditText.layoutManager = pickerLayoutManager
        binding.rvEditText.adapter = adapter

        pickerLayoutManager.setOnScrollStopListener { view ->
            binding.rvColorBg.visibility=View.VISIBLE
            val position = binding.rvEditText.getChildAdapterPosition(view)
            val selectedItem = list?.get(position)
            binding.tvNameEdit.text = selectedItem?.text
            when(selectedItem?.text){
                "Color" ->{setupRecyclerView(requireContext(),listColor!!)}
                "Font" ->{setupRecyclerView(requireContext(),listFont!!)}
                "Size" ->{}
                "Alignment"->{}
                "Case" ->{}
                "Shadow"->{}
                "Stroke"->{}
            }
            Log.d("selectedItem", "onCreate: ${selectedItem?.text}")
        }
    }
    fun setupRecyclerView(context: Context, itemList: List<ItemColorModel>): RecyclerView {
        if (!::adapterItem.isInitialized) {
            adapterItem = ItemPickAdapter(requireContext(), itemList, binding.rvColorBg)
        } else {
            adapterItem.swapData(itemList)
        }

        adapterItem.notifyDataSetChanged()
        val pickerLayoutManager = PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)
        pickerLayoutManager.changeAlpha = true
        pickerLayoutManager.scaleDownBy = 0.99f
        pickerLayoutManager.scaleDownDistance = 0.8f

        snapHelper.attachToRecyclerView(binding.rvEditText)
        binding.rvColorBg.layoutManager = pickerLayoutManager
        binding.rvColorBg.adapter = adapterItem
        pickerLayoutManager.setOnScrollStopListener { view ->
            val position = binding.rvColorBg.getChildAdapterPosition(view)
            val selectedItem = itemList?.get(position)
            when(selectedItem?.text){
                "Color" ->{
                  preferences.setString("text_color",selectedItem.rs)
                    binding.tvContent.setTextColor(Color.parseColor(selectedItem.rs))}
                "Font" ->{
                    val fontValue = ResourcesCompat.getFont(context, selectedItem.rs.toInt())?.toString()
                    preferences.setString("font", fontValue ?: "")
                    binding.tvContent.typeface = ResourcesCompat.getFont(context, selectedItem.rs.toInt())}
                "Size" ->{}
                "Alignment"->{}
                "Case" ->{}
                "Shadow"->{}
                "Stroke"->{}

            }
        }
        return binding.rvColorBg
    }




    override fun onResume() {
        super.onResume()
        checkAndLoadImage()
    }

    private fun checkAndLoadImage() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            loadImageFromSharedPreferences()
        } else {
            requestStoragePermission()
        }
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun setUpTabLayOutBackground() {
        binding.tabLayoutBg.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.gray
            )
        )
        val tabLayout = binding.tabLayoutBg
        val tab1 = tabLayout.newTab()
        tab1.text = "Library"
        tab1.setIcon(R.drawable.icon_libary)

        val tab2 = tabLayout.newTab()
        tab2.text = "Unsplash"
        tab2.setIcon(R.drawable.icon_unsplash)

        val tab3 = tabLayout.newTab()
        tab3.text = "Color"
        tab3.setIcon(R.drawable.icon_color)
        tabLayout.addTab(tab1)
        tabLayout.addTab(tab2)
        tabLayout.addTab(tab3)

        binding.tabLayoutBg.getTabAt(0)?.view?.setOnClickListener {
            binding.rvColorBg.visibility= View.GONE
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.tabLayoutBg.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {

                    1 -> {
                        (activity as MainActivity).replaceFragment(UnSplashFragment())
                    }

                    2 -> {
                        setColorRecyclerView()
                        binding.rvColorBg.visibility= View.VISIBLE
                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setColorRecyclerView() {
        val pickerLayoutManager = PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)
        pickerLayoutManager.changeAlpha = true
        pickerLayoutManager.scaleDownBy = 0.99f
        pickerLayoutManager.scaleDownDistance = 0.8f
        adapterItem = ItemPickAdapter(requireContext(), listColor!!, binding.rvColorBg)
        snapHelper.attachToRecyclerView(binding.rvEditText)
        binding.rvColorBg.layoutManager = pickerLayoutManager
        binding.rvColorBg.adapter = adapterItem


        pickerLayoutManager.setOnScrollStopListener { view ->
            val position = binding.rvColorBg.getChildAdapterPosition(view)
            val selectedItem = listColor?.get(position)
            if (selectedItem != null) {
//                preferences.setString("bg_color",selectedItem.rs)
                binding.ivBg.setImageResource(selectedItem.rs.toInt())
            }
        }

    }

    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }
        binding.tvSave.setOnClickListener {
            val color = preferences.getString("text_color")
//            val bgColor = preferences.getString("bg_color")
            val imageUri = preferences.getString("imageUri")
            val font = preferences.getString("text_font")
            Log.d("font", "setUpListener: $font")
            Log.d("color", "setUpListener: $imageUri")
            val bundle = Bundle().apply {
                    putString("text_color", color?:"")
//                    putString("bg_color",bgColor?:"")
                    putString("text_font",font)
                    putString("imageUri",imageUri)

            }
            // Khởi tạo HomeFragment và truyền Bundle vào
            val homeFragment = HomeFragment().apply {
                arguments = bundle
            }
            (activity as MainActivity).replaceFragment(homeFragment)
        }
        binding.tvCancel.setOnClickListener {
            (activity as MainActivity).replaceFragment(HomeFragment())
        }


    }

    private fun setUpTabLayOut() {
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
                        binding.rvColorBg.visibility= View.GONE

                    }
                    1 -> {
                        binding.ivName.text = getString(R.string.text_editing)
                        binding.tabLayoutBg.visibility = View.GONE
                        binding.relativeLayout.visibility = View.VISIBLE
                        binding.rvColorBg.visibility= View.GONE
                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }


}
