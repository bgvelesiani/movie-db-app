package com.gvelesiani.movieapp.presentation.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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
import com.gvelesiani.movieapp.other.extensions.isNetworkAvailable
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
        setUpRecyclerViewWithAdapter()
        setupListeners()
        setUpObservers()
    }

    private fun setUpRecyclerViewWithAdapter() {
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
                footer = MovieLoadStateAdapter(requireContext()) { recyclerViewAdapter.retry() },
                header = MovieLoadStateAdapter(requireContext()) { recyclerViewAdapter.retry() })
        }
    }

    private fun setupListeners() {
        recyclerViewAdapter.addLoadStateListener {
            binding.progressBar.isVisible = it.refresh is LoadState.Loading
        }

        binding.swLayout.setOnRefreshListener {
            if (recyclerViewAdapter.itemCount == 0 && requireContext().isNetworkAvailable) {
                setUpObservers()
                binding.swLayout.isRefreshing = false
            } else {
                recyclerViewAdapter.submitData(lifecycle, PagingData.empty())
                setUpObservers()
                binding.swLayout.isRefreshing = false

            }
        }
    }

    private fun setUpObservers() {
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