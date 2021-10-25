package com.gvelesiani.movieapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VM : BaseViewModel, B : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: B

    private lateinit var viewModel: VM

    abstract val bindingInflater: (LayoutInflater) -> B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater).apply {
            setContentView(root)
        }
        viewModel = provideViewModel()
        setupView(savedInstanceState)
    }

    fun getViewModel(): VM {
        return viewModel
    }

    abstract fun provideViewModel(): VM

    abstract fun setupView(savedInstanceState: Bundle?)
}