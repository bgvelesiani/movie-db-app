package com.gvelesiani.movieapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.gvelesiani.movieapp.R
import com.gvelesiani.movieapp.base.BaseActivity
import com.gvelesiani.movieapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate


    override fun setupView(binding: ActivityMainBinding, savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.home_graph)

        if (FirebaseAuth.getInstance().currentUser != null) {
            graph.startDestination = R.id.moviesFragment
            navController.graph = graph
        }
    }

    override fun provideViewModel(): MainViewModel = viewModel

}