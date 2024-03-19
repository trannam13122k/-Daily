package com.example.daily.ui.fragment.mainFragment.contentMain

import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentContentBinding
import com.example.daily.model.EditModel
import com.example.daily.model.FavouriteModel
import com.example.daily.ui.fragment.themes.edit.EditViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ContentMainFragment() : BaseFragment<FragmentContentBinding>() {

    private lateinit var viewModel: EditViewModel

    private var contentMainModel :String ?=null


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentContentBinding {
     return FragmentContentBinding.inflate(inflater)
    }

    override fun init() {

        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)

        val bundleN = arguments
        if (bundleN != null) {
            contentMainModel = bundleN["question_object"] as String
            if (contentMainModel != null) {
                binding.tvContent.setText(contentMainModel)
            }
        }

        binding.imgFavourite.setOnClickListener {

            Toast.makeText(requireContext(),"Favourite ",Toast.LENGTH_SHORT).show()
            val senderRealTime = System.currentTimeMillis()
                val date = Date(senderRealTime)
                val format = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
                val formattedDateTime = format.format(date)
                var favourite = FavouriteModel(nameFavourite = contentMainModel!!, isFavourite = true, day = formattedDateTime)
                Log.d("favourite", "handleDataContent: $favourite")
                viewModel.insert(favourite)

        }
    }

    override fun setUpView() {

        setData()

    }

    private fun setData() {
        viewModel.allEdit.observe(viewLifecycleOwner) { editList ->
            if (editList.isEmpty()) {
                Log.d("edit", "setData Null: ${editList.size}")
                binding.tvContent.typeface =
                    ResourcesCompat.getFont(requireContext(), R.font.amatic_bold)
                binding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
                binding.tvContent.gravity = Gravity.CENTER
                binding.tvContent.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
            } else {
                val lastEdit = editList.last()
                setCaseText(lastEdit)
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
            "UpperCase" -> {
                binding.tvContent.text = binding.tvContent.text.toString()?.toUpperCase()
            }

            "UpperCaseAndLowerCase" -> {
                var name = binding.tvContent.text.toString().trim()
                var firstLetter = name.substring(0, 1)
                val remainingLetters = name.substring(1, name.length).toLowerCase()

                firstLetter = firstLetter.toUpperCase();
                name = firstLetter + remainingLetters;
                binding.tvContent.text = name
            }

            "LowerCase" -> {
                binding.tvContent.text = binding.tvContent.text.toString()?.toLowerCase()
            }
        }
    }

}