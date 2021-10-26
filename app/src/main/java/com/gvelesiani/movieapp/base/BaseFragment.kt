package com.gvelesiani.movieapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding> : Fragment() {

    private lateinit var viewModel: VM

    private var _binding: B? = null
    val binding get() = _binding!!

    abstract fun provideViewModel(): VM

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    abstract fun setupView(savedInstanceState: Bundle?)

    fun getViewModel(): VM {
        return viewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = provideViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}