package com.gvelesiani.movieapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gvelesiani.movieapp.R
import com.gvelesiani.movieapp.base.BaseActivity
import com.gvelesiani.movieapp.databinding.ActivityMainBinding
import com.gvelesiani.movieapp.other.extensions.isNetworkAvailable
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModel()

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        isNetworkAvailable()

        //binding.vp.adapter = ViewPagerFragmentAdapter(this)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun isNetworkAvailable() {
        if (!this.isNetworkAvailable) {
            Toast.makeText(this, "Please connect to a Network", Toast.LENGTH_SHORT).show()
        }
    }

    override fun provideViewModel(): MainViewModel = viewModel
}

//class ViewPagerFragmentAdapter(fragmentActivity: FragmentActivity) :
//    FragmentStateAdapter(fragmentActivity) {
//    override fun createFragment(position: Int): Fragment {
//        when (position) {
//            0 -> return MoviesFragment()
//            1 -> return SearchFragment()
//        }
//        return MoviesFragment()
//    }
//
//    override fun getItemCount(): Int {
//        return 2
//    }
//}
