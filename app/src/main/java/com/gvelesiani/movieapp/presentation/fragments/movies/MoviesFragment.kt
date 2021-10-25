package com.gvelesiani.movieapp.presentation.fragments.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvelesiani.movieapp.base.BaseFragment
import com.gvelesiani.movieapp.databinding.FragmentMoviesBinding
import com.gvelesiani.movieapp.other.adapter.MovieListAdapter
import com.gvelesiani.movieapp.other.extensions.gone
import com.gvelesiani.movieapp.other.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment<MoviesViewModel, FragmentMoviesBinding>() {

    private val viewModel: MoviesViewModel by viewModel()

    private val recyclerViewAdapter = MovieListAdapter {
        val action =
            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it)
        findNavController().navigate(action)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoviesBinding
        get() = FragmentMoviesBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setUpRecyclerViewWithAdapter()
        viewModel.getPopularMovies(1)

        setUpObservers()
    }

//    private fun setupSearchView() {
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//        })
//    }

    private fun setUpRecyclerViewWithAdapter() {
        binding.rvMovies.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObservers() {
        viewModel.movies.observe(this, { list ->
            recyclerViewAdapter.addData(list.movieResults)
            recyclerViewAdapter.notifyDataSetChanged()
        })

        viewModel.loader.observe(this, { showLoader ->
            if (showLoader) {
                binding.progressBar.visible()
            } else {
                binding.progressBar.gone()
            }
        })
    }

    override fun provideViewModel(): MoviesViewModel = viewModel

}