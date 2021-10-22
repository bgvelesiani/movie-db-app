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


    private var viewBinding: T? = null
    private val binding get() = viewBinding!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(viewBinding).root
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
        viewBinding = null
    }

    fun getViewModel(): VIEW_MODEL {
        return viewModel
    }
}