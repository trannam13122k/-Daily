package com.example.daily.ui.fragment.mainFragment.contentMain

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentContentBinding
import com.example.daily.model.EditModel
import com.example.daily.model.FavouriteModel
import com.example.daily.ui.fragment.themes.edit.EditViewModel
import com.example.daily.util.KeyWord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ContentMainFragment() : BaseFragment<FragmentContentBinding>() {

    private lateinit var viewModel: EditViewModel
    private var contentTitle: String? = null
    private var isFavourite: Boolean = false

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentContentBinding {
        return FragmentContentBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this)[EditViewModel::class.java]
        val bundleN = arguments
        if (bundleN != null) {
            contentTitle = bundleN[KeyWord.question_object] as String
            contentTitle?.let { contentTitle ->
                binding.tvContent.setText(contentTitle)
            }
        }

        binding.imgFavourite.setOnClickListener {
            isFavourite = !isFavourite
            if (isFavourite) {
                binding.imgFavourite.setImageResource(R.drawable.icon_true_fav)
                val senderRealTime = System.currentTimeMillis()
                val date = Date(senderRealTime)
                val format = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
                val formattedDateTime = format.format(date)
                val favourite = FavouriteModel(
                    nameFavourite = contentTitle!!,
                    isFavourite = true,
                    day = formattedDateTime
                )
                viewModel.insert(favourite)
            } else {
                binding.imgFavourite.setImageResource(R.drawable.icon_fav)
            }
        }
    }

    override fun setUpView() {
        setData()
    }

    @SuppressLint("ResourceAsColor")
    private fun setData() {
        viewModel.allEdit.observe(viewLifecycleOwner) { editList ->
            if (editList.isEmpty()) {
                binding.tvContent.apply {
                    typeface = ResourcesCompat.getFont(requireContext(), R.font.amatic_bold)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
                    gravity = Gravity.CENTER
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
            } else {
                val lastEdit = editList.last()
                setCaseText(lastEdit)
                binding.tvContent.apply {
                    typeface = ResourcesCompat.getFont(
                        requireContext(),
                        lastEdit.font ?: R.font.amatic_bold
                    )
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, lastEdit.size?.toFloat() ?: 30F)
                    gravity = lastEdit.alignment ?: Gravity.CENTER
                    setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            lastEdit.textColor ?: R.color.black
                        )
                    )
                }
            }
        }
}

    private fun setCaseText(lastEdit: EditModel) {
        when (lastEdit.textTransform) {
            KeyWord.upperCase -> {
                binding.tvContent.text = binding.tvContent.text.toString()?.toUpperCase()
            }

            KeyWord.upperCaseAndLowerCase -> {
                val name = binding.tvContent.text.toString().trim()
                val firstLetter = name.substring(0, 1).toUpperCase()
                val remainingLetters = name.substring(1).toLowerCase()
                val transformedText = firstLetter + remainingLetters
                binding.tvContent.text = transformedText
            }

            KeyWord.lowerCase -> {
                binding.tvContent.text = binding.tvContent.text.toString()?.toLowerCase()
            }
        }
    }

}

