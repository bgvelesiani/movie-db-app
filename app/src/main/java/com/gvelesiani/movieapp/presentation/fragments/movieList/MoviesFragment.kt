package com.gvelesiani.movieapp.presentation.fragments.movieList

import android.annotation.SuppressLint
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
import com.gvelesiani.movieapp.domain.ConnectionLiveData
import com.gvelesiani.movieapp.other.adapter.MovieListAdapter
import com.gvelesiani.movieapp.other.adapter.MovieLoadStateAdapter
import com.gvelesiani.movieapp.other.extensions.gone
import com.gvelesiani.movieapp.other.extensions.isNetworkAvailable
import com.mikepenz.itemanimators.AlphaInAnimator
import kotlinx.android.synthetic.main.load_state_footer_view_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesFragment : BaseFragment<MoviesViewModel, FragmentMoviesBinding>() {

    private lateinit var connectionLiveData: ConnectionLiveData
    private val viewModel: MoviesViewModel by viewModel()

    private val recyclerViewAdapter = MovieListAdapter {
        val action =
            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it)
        findNavController().navigate(action)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoviesBinding
        get() = FragmentMoviesBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionLiveData(requireContext())
        setupListeners()
        setupRecyclerViewWithAdapter()
        checkNetworkAndSetupObservers()
    }

    private fun setupRecyclerViewWithAdapter() {
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter(requireContext()) { recyclerViewAdapter.retry() })
            itemAnimator = AlphaInAnimator()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupListeners() {
        binding.swLayout.setOnRefreshListener {
            if (requireContext().isNetworkAvailable) {
                emptyRecyclerView(viewLifecycleOwner.lifecycleScope)
                // add new data to rv
                getItems()
            }
            binding.swLayout.isRefreshing = false
        }

        recyclerViewAdapter.addLoadStateListener {
            binding.progressBar.isVisible = it.refresh is LoadState.Loading
        }
    }

    private fun checkNetworkAndSetupObservers() {
        connectionLiveData.observe(this) { isConnected ->
            if (isConnected) {
                binding.tvNoInternet.gone()
                if (recyclerViewAdapter.itemCount > 0) {
                    binding.progressBar.gone()
                } else {
                    getItems()
                }
            } else {
                if (recyclerViewAdapter.itemCount > 0) {
                    binding.tvNoInternet.gone()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getItems() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMovies().collectLatest { pagingData ->
                recyclerViewAdapter.submitData(pagingData)
                recyclerViewAdapter.notifyDataSetChanged()
            }

            recyclerViewAdapter.loadStateFlow.collectLatest { loadStates ->
                pfRetryState.isVisible = loadStates.refresh !is LoadState.Loading
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun emptyRecyclerView(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            recyclerViewAdapter.submitData(PagingData.empty())
            recyclerViewAdapter.notifyDataSetChanged()
        }
    }

    override fun provideViewModel(): MoviesViewModel = viewModel

}