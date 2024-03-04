package com.example.notisave.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.daily.MainActivity

abstract class BaseFragment<VB : ViewBinding> :Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!
  protected  var activity : MainActivity? = null

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getViewBinding(inflater, container)
        init()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = this.getActivity() as MainActivity

        setUpView()
    }

    abstract fun init()
    abstract fun setUpView()
}