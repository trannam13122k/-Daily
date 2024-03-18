package com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn.newAddDataYourOwn


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.daily.base.BaseFragment
import com.example.daily.databinding.FragmentNewAddBinding
import com.example.daily.model.AddModel
import com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn.AddYourOwnViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class NewAddFragment : BaseFragment<FragmentNewAddBinding>() {


    private lateinit var viewModel: AddYourOwnViewModel

    private var addModel: AddModel?=null

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewAddBinding {
        return FragmentNewAddBinding.inflate(inflater)
    }

    override fun init() {
        viewModel = ViewModelProvider(this).get(AddYourOwnViewModel::class.java)
        addModel = arguments?.getParcelable("addModel")
        if (addModel != null) binding.edtAdd.setText(addModel!!.nameAdd) else binding.edtAdd.setText("")
    }

    override fun setUpView() {
        setUpListener()
    }
    private fun setUpListener() {
        binding.ivBack.setOnClickListener {
           activity?.onBackPressed()
        }
        binding.btnSave.setOnClickListener{
            if(addModel !=null){
                handleBtnEdit(addModel!!)
            }
            else{
                handleBtnSave()
            }

        }
    }

    private fun handleBtnEdit(addModel: AddModel) {
        val text = binding.edtAdd.text.toString().trim()
        if(text.isEmpty()){
            Toast.makeText(requireContext(), "Please enter data", Toast.LENGTH_SHORT).show()
        }
        else{
            val editContent = AddModel(id=addModel.id ,nameAdd = text, nameCollection = addModel.nameCollection, isFavourite = false, day = addModel.day)
            viewModel.updateContent(editContent)
            activity?.onBackPressed()

        }
    }

    private fun handleBtnSave() {
        val text = binding.edtAdd.text.toString().trim()
        if(text.isEmpty()){
            Toast.makeText(requireContext(),"Please enter data into EditText", Toast.LENGTH_SHORT).show()
        }
        else{
            val senderRealTime = System.currentTimeMillis()
            val date = Date(senderRealTime)
            val format = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
            val formattedDateTime = format.format(date)
            val newContent = AddModel(nameAdd = text, nameCollection = "", isFavourite = false, day = formattedDateTime)
            viewModel.insertContent(newContent)
            activity?.onBackPressed()
        }
    }

}