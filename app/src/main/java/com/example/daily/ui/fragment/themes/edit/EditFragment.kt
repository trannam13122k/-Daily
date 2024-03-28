package com.example.daily.ui.fragment.themes.edit

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentEditBinding
import com.example.daily.model.EditModel
import com.example.daily.ui.fragment.mainFragment.MainFragment
import com.example.daily.ui.fragment.themes.edit.backGroundEditing.colorEdittingBG.ColorAdapter
import com.example.daily.ui.fragment.themes.edit.backGroundEditing.unsplash.UnSplashFragment
import com.example.daily.ui.fragment.themes.edit.textEditing.textEffect.PickerItemAdapter
import com.example.daily.ui.fragment.themes.edit.textEditing.textEffect.pickerItem.ItemPickerAdapter
import com.example.daily.ui.fragment.themes.edit.textEditing.textEffect.pickerItem.ItemPickerModel

import com.example.daily.util.DataB
import com.example.daily.util.KeyWord
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
    private lateinit var viewModel: EditViewModel

    private var type: String = "Color"
    private var imageBg: String = ""
    private var colorBg: Int = 0
    private var textColor: Int = 0
    private var size: Int = 0
    private var alignment: Int = 0
    private var font: Int = 0
    private var textTransform: String = ""

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
        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)
        clickListener()
    }

    override fun setUpView() {
        setUpTapLayoutMain()
        tabLayoutBackGroundEditing()
        setUpTextEditing()
        setUpViewAll()
        colorEditing()
    }

    private fun setUpViewAll() {
        viewModel.allEdit.observe(viewLifecycleOwner) { editList ->
            if (editList.isEmpty()) {
                textColor = R.color.white
                size = 30
                alignment = Gravity.CENTER
                font = R.font.amatic_bold
                binding.tvContent.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.amatic_bold)
                binding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
                binding.tvContent.gravity = Gravity.CENTER
                binding.tvContent.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.color_six
                    )
                )
            } else {
                val lastEdit = editList.last()
                setBackGround(lastEdit)
                setCaseText(lastEdit)
                textColor = lastEdit.textColor
                size = lastEdit.size
                alignment = lastEdit.alignment
                font = lastEdit.font
                binding.tvContent.typeface =
                    ResourcesCompat.getFont(requireContext(), lastEdit.font ?: R.font.amatic_bold)
                binding.tvContent.setTextSize(
                    TypedValue.COMPLEX_UNIT_SP,
                    lastEdit.size.toFloat() ?: 30F
                )
                binding.tvContent.gravity = lastEdit.alignment ?: Gravity.CENTER
                binding.tvContent.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        lastEdit.textColor ?: R.color.white
                    )
                )
            }

        }
    }

    private fun setCaseText(lastEdit: EditModel) {
        when (lastEdit.textTransform) {
            KeyWord.upperCase -> {
                binding.tvContent.text = binding.tvContent.text.toString()?.toUpperCase()
            }

            KeyWord.upperCaseAndLowerCase -> {
                var name = binding.tvContent.text.toString().trim()
                var firstLetter = name.substring(0, 1)
                val remainingLetters = name.substring(1, name.length).toLowerCase()

                firstLetter = firstLetter.toUpperCase();
                name = firstLetter + remainingLetters;

                binding.tvContent.text = name
                textTransform = KeyWord.upperCaseAndLowerCase
            }

            KeyWord.lowerCase -> {
                binding.tvContent.text = binding.tvContent.text.toString()?.toLowerCase()
            }
        }
    }

    private fun setBackGround(lastEdit: EditModel) {
        imageBg = lastEdit.imageBg
        if (lastEdit.imageBg.isNotEmpty()) {
            Glide.with(requireContext())
                .load(imageBg)
                .into(binding.ivBg)
        } else {
            binding.ivBg.setBackgroundResource(lastEdit.imageColor)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->

            if (uri != null) {
                binding.ivBg?.setImageURI(uri)
                saveImageUriToSharedPreferences(uri.toString())
                imageBg = uri.toString()
                colorBg = 0
            } else {
                loadImageFromSharedPreferences()
            }
        }
    }

    private fun loadImageFromSharedPreferences() {
        CoroutineScope(Dispatchers.IO).launch {
            val imageUri = preferences.getString(KeyWord.imageBg)
            if (!imageUri.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    binding.ivBg?.setImageURI(Uri.parse(imageUri))
                }
            }
        }
    }

    private fun saveImageUriToSharedPreferences(imageUri: String) {
        preferences.setString(KeyWord.imageBg, imageUri)
    }

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
            viewModel.allEdit.observe(viewLifecycleOwner) { edit ->
                if (edit.isEmpty()) {
                    var editAdd = EditModel(
                        imageBg = imageBg,
                        imageColor = colorBg,
                        imageUnSplashFragment = 0,
                        textColor = textColor,
                        font = font,
                        size = size,
                        alignment = alignment,
                        textTransform = textTransform
                    )
                    viewModel.insertEdit(editAdd)
                } else {
                    var lastEdit = edit.last()
                    var edit = EditModel(
                        id = lastEdit.id,
                        imageBg = imageBg ?: lastEdit.imageBg,
                        imageColor = colorBg ?: lastEdit.imageColor,
                        imageUnSplashFragment = 0,
                        textColor = textColor ?: R.color.black,
                        font = font ?: R.font.amatic_bold,
                        size = size ?: 30,
                        alignment = alignment ?: Gravity.CENTER,
                        textTransform = textTransform,
                    )
                    viewModel.updateEdit(edit)
                }
            }

            openFragment(MainFragment::class.java, null, true)
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

    private fun tabLayoutBackGroundEditing() {
        val color = binding.tabLayoutBg.newTab()
        val library = binding.tabLayoutBg.newTab()
        val unSplash = binding.tabLayoutBg.newTab()

        library.text = resources.getString(R.string.library)
        unSplash.text = resources.getString(R.string.unSplash)
        color.text = resources.getString(R.string.color)

        library.setIcon(R.drawable.icon_libary)
        unSplash.setIcon(R.drawable.icon_unsplash)
        color.setIcon(R.drawable.icon_color)

        binding.tabLayoutBg.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.gray
            )
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
            binding.ivBg?.setImageDrawable(ColorDrawable(DataB.colorList.get(0)))

            binding.rcvItem.visibility = View.VISIBLE
            colorAdapter.notifyDataSetChanged()
            binding.rcvItem.adapter = colorAdapter

        }
    }

    private fun colorEditing() {
        with(binding) {
            relativeLayout.visibility = View.GONE

            val pickerLayoutManager =
                PickerLayoutManager(requireContext(), PickerLayoutManager.HORIZONTAL, false)

            colorAdapter = ColorAdapter(DataB.listDrawableColors)
            snapHelper.attachToRecyclerView(rvEditText)

            pickerLayoutManager.apply {
                changeAlpha = true
                scaleDownBy = 0.99f
                scaleDownDistance = 0.8f
            }
            rcvItem.layoutManager = pickerLayoutManager
            rcvItem.adapter = colorAdapter

            pickerLayoutManager.setOnScrollStopListener { view ->
                val position = rcvItem.getChildAdapterPosition(view)

                if (position in 0 until DataB.colorList.size) {
                    try {
                        setEffect(type, position)
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(requireContext(), "Please Select The Image ", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    fun setEffect(type: String, position: Int) {
        if (binding.tabLayout.getTabAt(0)?.isSelected == true) {
            binding.ivBg.setBackgroundResource(DataB.colorList[position])
            imageBg = ""
            binding.ivBg?.setImageURI(null)
            colorBg = DataB.colorList[position]
        } else {
            when (type) {
                KeyWord.color -> {
                    context?.getColor(DataB.colorList[position])
                        ?.let { binding.tvContent.setTextColor(it) }
                    textColor = DataB.colorList[position]
                }

                KeyWord.font -> {
                    binding.tvContent.typeface =
                        ResourcesCompat.getFont(requireContext(), DataB.listFont.get(position).font)
                    font = DataB.listFont.get(position).font
                }

                KeyWord.size -> {
                    binding.tvContent.setTextSize(
                        TypedValue.COMPLEX_UNIT_SP,
                        DataB.listSize.get(position).size.toFloat()
                    )
                    size = DataB.listSize.get(position).size
                }

                KeyWord.alignment -> {
                    binding.tvContent.gravity =
                        DataB.listAligment[position].alignment or Gravity.CENTER
                    alignment = DataB.listAligment[position].alignment or Gravity.CENTER
                }

                KeyWord.alignmentTop -> {
                    binding.tvContent.gravity =
                        DataB.listAligmentTop[position].alignmentTop or Gravity.CENTER
                    alignment = DataB.listAligmentTop[position].alignmentTop or Gravity.CENTER
                }

                KeyWord.case -> {
                    if (position == 0) {
                        binding.tvContent.text = binding.tvContent.text.toString()?.toUpperCase()
                        textTransform = KeyWord.upperCase
                    } else if (position == 1) {
                        var name = binding.tvContent.text.toString().trim()
                        var firstLetter = name.substring(0, 1)
                        val remainingLetters = name.substring(1, name.length).toLowerCase()

                        firstLetter = firstLetter.toUpperCase();
                        name = firstLetter + remainingLetters;

                        binding.tvContent.text = name
                        textTransform = KeyWord.upperCaseAndLowerCase

                    } else binding.tvContent.text = binding.tvContent.text.toString()?.toLowerCase()
                    textTransform = KeyWord.lowerCase
                }

                KeyWord.shadow -> {
                    showAlertDialog(
                        requireContext(),
                        "Notification",
                        "The function is currently updating"
                    )
                }

                KeyWord.stroke -> {
                    showAlertDialog(
                        requireContext(),
                        "Notification",
                        "The function is currently updating"
                    )
                }
            }
        }
    }

    fun showAlertDialog(context: Context, title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
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

        pickerItemAdapter =
            PickerItemAdapter(requireContext(), DataB.listTextEditings!!, binding.rvEditText)
        binding.rvEditText.adapter = pickerItemAdapter

        binding.rcvItem.visibility = View.VISIBLE

        pickerLayoutManager.setOnScrollStopListener { view ->
            val position = binding.rvEditText.getChildAdapterPosition(view)
            val selectedItem = DataB.listTextEditings?.get(position)
            binding.tvNameEdit.text = selectedItem?.text

            when (selectedItem?.text) {
                KeyWord.color -> {
                    type = KeyWord.color
                    setupRecyclerView(requireContext(), DataB.listColor)
                }

                KeyWord.font -> {
                    type = KeyWord.font
                    setupRecyclerView(requireContext(), DataB.listFont)
                }

                KeyWord.size -> {
                    type = KeyWord.size
                    setupRecyclerView(requireContext(), DataB.listSize)
                }

                KeyWord.alignment -> {
                    type = KeyWord.alignment
                    setupRecyclerView(requireContext(), DataB.listAligment)
                }

                KeyWord.alignmentTop -> {
                    type = KeyWord.alignmentTop
                    setupRecyclerView(requireContext(), DataB.listAligmentTop)
                }

                KeyWord.case -> {
                    type = KeyWord.case
                    setupRecyclerView(requireContext(), DataB.listCase)
                }

                KeyWord.shadow -> {
                    type = KeyWord.shadow
                    setupRecyclerView(requireContext(), DataB.listColor)
                }

                KeyWord.stroke -> {
                    type = KeyWord.stroke
                    setupRecyclerView(requireContext(), DataB.listStroke)
                }
            }
        }
    }

    fun setupRecyclerView(context: Context, itemList: List<ItemPickerModel>): RecyclerView {
        if (!::adapterItem.isInitialized) {
            adapterItem = ItemPickerAdapter(context, itemList, binding.rcvItem)
        } else {
            adapterItem.swapData(itemList)
        }

        adapterItem.notifyDataSetChanged()

        val pickerLayoutManager =
            PickerLayoutManager(context, PickerLayoutManager.HORIZONTAL, false).apply {
                changeAlpha = true
                scaleDownBy = 0.99f
                scaleDownDistance = 0.8f
            }

        snapHelper.attachToRecyclerView(binding.rvEditText)
        binding.rcvItem.layoutManager = pickerLayoutManager
        binding.rcvItem.adapter = adapterItem

        pickerLayoutManager.setOnScrollStopListener { view ->
            val position = binding.rcvItem.getChildAdapterPosition(view)
            try {
                setEffect(type, position)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }

        return binding.rcvItem
    }


}