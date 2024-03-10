package com.example.daily.ui.fragment.edit

import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentEditBinding
import com.example.daily.ui.fragment.edit.colorEdittingBG.ColorAdapter
import com.example.daily.ui.fragment.edit.colorEdittingBG.ColorsBG
import com.example.daily.ui.fragment.edit.unsplash.UnSplashFragment
import com.example.daily.ui.fragment.themes.titleBG.TitleBackground
import com.example.daily.util.PickerLayoutManager
import com.example.daily.util.Utils
import com.google.android.material.tabs.TabLayout


class EditFragment : BaseFragment<FragmentEditBinding>() {

    private lateinit var launcher: ActivityResultLauncher<PickVisualMediaRequest>

    private lateinit var colorAdapter: ColorAdapter


    var listColors: List<ColorsBG>? = null


    private val snapHelper: SnapHelper = LinearSnapHelper()


    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentEditBinding {
        return FragmentEditBinding.inflate(inflater)
    }

    override fun init() {
        clickListener()
    }

    override fun setUpView() {
//        listColorData()

        setUpTapLayoutMain()
        tabLayoutEditing()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.ivBg?.setImageURI(uri)
            } else {
                Toast.makeText(requireContext(), "1212313232 - no chekc ", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun clickListener() {
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
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
//                        binding.ivName.text = getString(R.string.text_editing)
//                        binding.tabLayoutBg.visibility = View.GONE
//                        binding.relativeLayout.visibility = View.VISIBLE
//                        binding.rvColorBg.visibility = View.GONE
                        Toast.makeText(requireContext(), "TextEdittoing", Toast.LENGTH_SHORT).show()
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

        binding.tabLayoutBg.getTabAt(0)?.view?.setOnClickListener {
            binding.rvColorBg.visibility = View.GONE
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            binding.ivBg?.setImageURI(null)
        }

        binding.tabLayoutBg.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {

                    1 -> {
                        clickUnsplash()
                    }

                    2 -> {
                        colorEditing()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun clickUnsplash() {
        binding.rvColorBg.visibility = View.GONE
//        openFragment(UnSplashFragment::class.java, null, true)
        Toast.makeText(requireContext(), "UnSplash", Toast.LENGTH_SHORT).show()

    }

    private fun colorEditing() {

        listColors = listOf(
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
            R.drawable.color_sixteen
        ).map { ColorsBG(it) }

        Toast.makeText(requireContext(), "Color", Toast.LENGTH_SHORT).show()
        binding.rvColorBg.visibility = View.VISIBLE

        val pickerLayoutManager = PickerLayoutManager(
            requireContext(),
            PickerLayoutManager.HORIZONTAL,
            false
        )  // bánh xe chọn màu
        pickerLayoutManager.changeAlpha = true    //thay đổi độ mờ của các mục
        pickerLayoutManager.scaleDownBy =
            0.99f   //giảm kích thước của các mục khi chúng di chuyển xa
        pickerLayoutManager.scaleDownDistance = 0.8f


        colorAdapter = ColorAdapter(listColors)
        snapHelper.attachToRecyclerView(binding.rvEditText)
        binding.rvColorBg.layoutManager = pickerLayoutManager

        binding.rvColorBg.apply {
            adapter = colorAdapter
        }


        pickerLayoutManager.setOnScrollStopListener { view ->       //lắng nghe được gọi khi cuộn dừng lại và hiển thị một mục cụ thể
            val position = binding.rvColorBg.getChildAdapterPosition(view)
            val selectedItem = listColors?.get(position)             //lấy vị trí của mục hiện tại
            if (selectedItem != null) {
//                binding.ivBg.setImageResource(selectedItem.colorCode.toInt())
                Toast.makeText(requireContext(), "Color" + selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
    }


//    fun colorEditing(){
//                Toast.makeText(requireContext(), "Color", Toast.LENGTH_SHORT).show()
//        colorAdapter = ColorAdapter(listColors)
//        binding.rvColorBg.apply {
//            adapter = colorAdapter
//        }
//    }

//    private fun listColorData()  {
//        val listNameColors = listOf(
//            "#5369EC", "#4BD392", "#EC8DF4", "#FFD6B0", "#B0FAFF", "#FFB0B0", "#EA6E14", "#2984C6",
//            "#3A4790", "#225E4F", "#C1DA28", "#791BB2", "#76412A", "#EFFD53", "#717797", "#16D0F9"
//        )
//        val listDrawableColors = listOf(
//            R.drawable.color_one,
//            R.drawable.color_two,
//            R.drawable.color_three,
//            R.drawable.color_four,
//            R.drawable.color_five,
//            R.drawable.color_six,
//            R.drawable.color_seven,
//            R.drawable.color_eight,
//            R.drawable.color_nine,
//            R.drawable.color_ten,
//            R.drawable.color_eleven,
//            R.drawable.color_twelve,
//            R.drawable.color_thirteen,
//            R.drawable.color_fourteen,
//            R.drawable.color_fifteen,
//            R.drawable.color_sixteen
//        )
//        val listTextColor = listOf("Color", "Color", "Color","Color", "Color", "Color","Color", "Color", "Color",
//            "Color", "Color", "Color","Color", "Color", "Color", "Color")
//
//        for (i in 0 until listNameColors.size) {
//            Log.d(TAG, "dataListColor: " + i)
//            listColors?.add(ColorsBG(listNameColors.get(i), listDrawableColors.get(i), listTextColor.get(i)))
//        }
//    }


    // permisstion
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
                Toast.makeText(
                    requireContext(), "Storage permission is required...", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

//cm