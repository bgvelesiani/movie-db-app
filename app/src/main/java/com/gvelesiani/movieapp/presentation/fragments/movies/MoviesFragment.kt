package com.gvelesiani.movieapp.presentation.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvelesiani.movieapp.base.BaseFragment
import com.gvelesiani.movieapp.databinding.FragmentMoviesBinding
import com.gvelesiani.movieapp.other.adapter.MovieListAdapter
import com.gvelesiani.movieapp.other.adapter.MovieLoadStateAdapter
import com.gvelesiani.movieapp.other.extensions.gone
import com.gvelesiani.movieapp.other.extensions.isNetworkAvailable
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.load_state_footer_view_item.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        setupListeners()
        setupRecyclerViewWithAdapter()
        setupObservers()

        if (recyclerViewAdapter.itemCount > 0) {
            binding.progressBar.gone()
        }
    }

    private fun setupRecyclerViewWithAdapter() {
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter(requireContext()) { recyclerViewAdapter.retry() })
            itemAnimator = LandingAnimator()
        }
    }

    private fun setupListeners() {
        binding.swLayout.setOnRefreshListener {
            if (recyclerViewAdapter.itemCount == 0 && requireContext().isNetworkAvailable) {
                setupObservers()
                binding.swLayout.isRefreshing = false
            } else {
                recyclerViewAdapter.submitData(lifecycle, PagingData.empty())
                setupObservers()
                binding.swLayout.isRefreshing = false
            }
        }

        recyclerViewAdapter.addLoadStateListener {
            binding.progressBar.isVisible = it.refresh is LoadState.Loading
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMovies().collectLatest { pagingData ->
                recyclerViewAdapter.submitData(pagingData)
            }

            recyclerViewAdapter.loadStateFlow.collectLatest {
                pfRetryState.isVisible = it.refresh !is LoadState.Loading
            }
        }
    }

    override fun provideViewModel(): MoviesViewModel = viewModel

}