package com.gvelesiani.movieapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VIEW_MODEL : BaseViewModel, T : ViewBinding> :
    AppCompatActivity() {

    private lateinit var viewModel: VIEW_MODEL

    private var viewBinding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> T

    @Suppress("UNCHECKED_CAST")
    protected val binding: T
        get() = viewBinding as T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(viewBinding).root)

        setupView(binding, savedInstanceState)

        viewModel = provideViewModel()
    }

    fun getViewModel(): VIEW_MODEL {
        return viewModel
    }

    abstract fun provideViewModel(): VIEW_MODEL

    abstract fun setupView(binding: T, savedInstanceState: Bundle?)


}