package com.example.daily.ui.fragment.mainFragment

import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.daily.R
import com.example.daily.base.BaseFragment
import com.example.daily.database.Preferences
import com.example.daily.databinding.FragmentMainBinding
import com.example.daily.model.EditModel
import com.example.daily.ui.fragment.adapter.ViewPager2Adapter
import com.example.daily.ui.fragment.categories.CategoriesFragment
import com.example.daily.ui.fragment.categories.model.Content
import com.example.daily.ui.fragment.mainFragment.contentMain.ContentTest
import com.example.daily.ui.fragment.settingDaiLy.settingMain.SettingFragment
import com.example.daily.ui.fragment.themes.ThemesFragment
import com.example.daily.ui.fragment.themes.edit.EditViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : BaseFragment<FragmentMainBinding>() {


    lateinit var titleContent: String


    private lateinit var viewModel: EditViewModel

    var adapter: ViewPager2Adapter? = null

    private var mListquestion: List<String>? = null

    private lateinit var preferences: Preferences

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    override fun init() {
        preferences = Preferences.getInstance(requireContext())
        viewModel = ViewModelProvider(this).get(EditViewModel::class.java)
    }


    override fun setUpView() {
        clickListener()
        viewPager2()
        setBackGround()
    }

    private fun setBackGround() {
        val randomImage = arguments?.getString("randomImage")
        if (randomImage != null) {
            Glide.with(requireContext())
                .load(randomImage)
                .into(binding.imgBgMain)
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                val edit = viewModel.getEdit()
                if (edit == null) {
                    var editAdd = EditModel(
                        imageBg = randomImage,
                        imageColor = 0,
                        textColor = R.color.black,
                        font = R.font.amatic_bold,
                        size = 30,
                        alignment = Gravity.CENTER,
                        textTransform = ""
                    )
                    viewModel.insertEdit(editAdd)
                } else {
                    edit.apply {
                        imageBg = randomImage
                    }
                    viewModel.updateEdit(edit)

                }
            }
        }else{
            viewModel.allEdit.observe(viewLifecycleOwner) { editList ->
                if (editList.isEmpty()) {

                } else {
                    val lastEdit = editList.last()
                    setBackGroundMain(lastEdit)
                }

            }
        }

        //set Category
        titleContent = preferences.getString("titleContent") ?: "General"
        binding.tvTitleContent.text = titleContent
    }

    private fun setBackGroundMain(lastEdit: EditModel) {
        if (lastEdit.imageBg.isNotEmpty()) {
            Glide.with(requireContext())
                .load(lastEdit.imageBg)
                .into(binding.imgBgMain)
        } else {
            binding.imgBgMain.setBackgroundResource(lastEdit.imageColor)
        }
    }

    override fun onResume() {
        super.onResume()
        preferences.setString("titleContent", titleContent!!)
        Log.d("titleContent", "${titleContent.toString()}")
    }

    private fun viewPager2() {

        mListquestion = preferences.getList("myListKey")
        if(mListquestion==null){
            mListquestion =
                listOf(
                " Love is the master key that opens the gates of happiness. - Oliver Wendell Holmes ",
                " The only way to do great work is to love what you do.- Steve Jobs ",
                " The only way to do great work is to love what you do. - Steve Jobs ",
                " Keep your face to the sunshine and you cannot see a shadow. - Helen Keller ",
                " Hope is being able to see that there is light despite all of the darkness.- Desmond Tutu ",
                " It's not how much you have, but how much you enjoy that makes happiness. - Charles Spurgeon ",
                " Life is a journey, not a destination. - Ralph Waldo Emerson ",
                " Love is the master key that opens the gates of happiness. - Oliver Wendell Holmes ",
                " The only way to do great work is to love what you do.- Steve Jobs ",
                " The only way to do great work is to love what you do. - Steve Jobs ",
                " Keep your face to the sunshine and you cannot see a shadow. - Helen Keller ",
                " Hope is being able to see that there is light despite all of the darkness.- Desmond Tutu ",
                " It's not how much you have, but how much you enjoy that makes happiness. - Charles Spurgeon ",
                " Life is a journey, not a destination. - Ralph Waldo Emerson ",)
        }
        adapter = ViewPager2Adapter(requireActivity(), mListquestion!!)
        binding.viewPager2.adapter = adapter
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
    }


    private fun clickListener() {

        binding.llGeneral.setOnClickListener {
            openFragment(CategoriesFragment::class.java, null, true)
        }

        binding.llPen.setOnClickListener {
            openFragment(ThemesFragment::class.java, null, true)
        }

        binding.llUser.setOnClickListener {
            openFragment(SettingFragment::class.java, null, true)
        }
    }

    fun onBackPressed() {
        activity?.finish()
    }

}