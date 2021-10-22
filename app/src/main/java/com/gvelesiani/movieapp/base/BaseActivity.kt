package com.gvelesiani.movieapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VIEW_MODEL : BaseViewModel, T : ViewBinding> :
    AppCompatActivity() {

    private lateinit var viewModel: VIEW_MODEL

    protected abstract val binding : T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView(binding, savedInstanceState)

        viewModel = provideViewModel()
    }

    fun getViewModel(): VIEW_MODEL {
        return viewModel
    }

    abstract fun provideViewModel(): VIEW_MODEL

    abstract fun setupView(binding: T, savedInstanceState: Bundle?)


}