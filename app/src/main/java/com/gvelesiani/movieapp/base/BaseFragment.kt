package com.gvelesiani.movieapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VIEW_MODEL : BaseViewModel, T : ViewBinding> :
    Fragment() {

    private lateinit var viewModel: VIEW_MODEL
    private var _binding: T? = null
    private val binding get() = _binding!!

    abstract fun setViewBinding(): T

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setViewBinding()
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(binding, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = provideViewModel()
    }

    abstract fun setupView(binding: T, savedInstanceState: Bundle?)

    abstract fun provideViewModel(): VIEW_MODEL

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getViewModel(): VIEW_MODEL {
        return viewModel
    }
}