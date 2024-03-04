package com.example.daily.ui.Edit.fragment


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.daily.PickerLayoutManager
import com.example.daily.R
import com.example.daily.databinding.FragmentEditTextScreensBinding
import com.example.daily.ui.Edit.adapter.PickerAdapter
import com.example.daily.ui.Edit.model.PickerItem
import com.example.notisave.base.BaseFragment

class EditTextScreensFragment :BaseFragment<FragmentEditTextScreensBinding>() {
    private lateinit var adapter: PickerAdapter

    private var list: List<PickerItem>? = null
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditTextScreensBinding {
        return FragmentEditTextScreensBinding.inflate(inflater)
    }

    override fun init() {
        database()

    }

    private fun database() {

    }

    private fun setDataRecycleView() {
        val pickerLayoutManager = PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)
        pickerLayoutManager.changeAlpha = true
        pickerLayoutManager.scaleDownBy = 0.99f
        pickerLayoutManager.scaleDownDistance = 0.8f
        list = listOf(
            PickerItem(R.drawable.icon_color, "Color"),
            PickerItem(R.drawable.icon_font, "Font"),
            PickerItem(R.drawable.icon_size, "Size"),
            PickerItem(R.drawable.icon_alignment, "Alignment"),
            PickerItem(R.drawable.icon_alignment_top, "Alignment"),
            PickerItem(R.drawable.icon_case, "Case"),
            PickerItem(R.drawable.icon_shadow, "Shadow"),
            PickerItem(R.drawable.icon_stroke, "Shadow")
        )
        adapter = PickerAdapter(requireContext(), list!!, binding.rvEditText)
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvEditText)
        binding.rvEditText.layoutManager = pickerLayoutManager
        binding.rvEditText.adapter = adapter


        pickerLayoutManager.setOnScrollStopListener { view ->
            val position = binding.rvEditText.getChildAdapterPosition(view)
            val selectedItem = list?.get(position)
            when(selectedItem?.text){
                "1"->{
                }
            }
            binding.tvNameEdit.text=selectedItem?.text
            Log.d("selectedItem", "onCreate: ${selectedItem?.text}")

        }
    }

    override fun setUpView() {
        setDataRecycleView()
        setUpListener()
    }

    private fun setUpListener() {

    }
}