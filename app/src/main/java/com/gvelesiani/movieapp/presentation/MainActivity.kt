package com.gvelesiani.movieapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.gvelesiani.movieapp.base.BaseActivity
import com.gvelesiani.movieapp.databinding.ActivityMainBinding
import com.gvelesiani.movieapp.other.extensions.isNetworkAvailable
import org.koin.androidx.viewmodel.ext.android.viewModel

import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.gvelesiani.movieapp.R


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate


    override fun setupView(binding: ActivityMainBinding, savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        setupObservers()
        isNetworkAvailable()
    }

    private fun setupObservers(){
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.welcomeFragment, true)
            .build()

        viewModel.isClicked.observe(this, {
            if(it){
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_welcomeFragment_to_moviesFragment, null, navOptions)
            }
        })
    }

    private fun isNetworkAvailable(){
        if(!this.isNetworkAvailable){
            Toast.makeText(this, "Please connect to a Network", Toast.LENGTH_SHORT).show()
        }
    }

    override fun provideViewModel(): MainViewModel = viewModel

}