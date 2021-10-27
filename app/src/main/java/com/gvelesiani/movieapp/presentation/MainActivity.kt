package com.gvelesiani.movieapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gvelesiani.movieapp.R
import com.gvelesiani.movieapp.base.BaseActivity
import com.gvelesiani.movieapp.databinding.ActivityMainBinding
import com.gvelesiani.movieapp.other.extensions.isNetworkAvailable
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MainViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        isNetworkAvailable()
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.moviesFragment, R.id.searchFragment, R.id.movieDetailsFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun isNetworkAvailable() {
        if (!this.isNetworkAvailable) {
            Toast.makeText(this, "Please connect to a Network", Toast.LENGTH_SHORT).show()
        }
    }

    override fun provideViewModel(): MainViewModel = viewModel
}
