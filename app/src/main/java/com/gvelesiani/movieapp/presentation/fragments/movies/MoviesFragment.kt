package com.gvelesiani.movieapp.presentation.fragments.movies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
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
import com.gvelesiani.movieapp.other.extensions.gone
import com.gvelesiani.movieapp.other.extensions.isNetworkAvailable
import kotlinx.android.synthetic.main.load_state_footer_view_item.*
import kotlinx.coroutines.CoroutineScope
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
        checkNetworkAndSetupObservers()
    }

    private fun setupRecyclerViewWithAdapter() {
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter(requireContext()) { recyclerViewAdapter.retry() })
            //itemAnimator = ScaleInAnimator()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupListeners() {
        binding.swLayout.setOnRefreshListener {
            if (requireContext().isNetworkAvailable) {
                emptyRecyclerView(viewLifecycleOwner.lifecycleScope)

                // add new data to rv
                checkNetworkAndSetupObservers()
            }
            binding.swLayout.isRefreshing = false
        }

        binding.btGoToSettings.setOnClickListener {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

        recyclerViewAdapter.addLoadStateListener {
            binding.progressBar.isVisible = it.refresh is LoadState.Loading
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun checkNetworkAndSetupObservers() {
        viewModel.connectionLiveData.observe(this, {
            if (it) {
                binding.btGoToSettings.gone()
                binding.tvNoInternet.gone()

                if (recyclerViewAdapter.itemCount > 0) {
                    binding.progressBar.gone()
                } else {
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
            } else {
                if (recyclerViewAdapter.itemCount > 0) {
                    binding.btGoToSettings.gone()
                    binding.tvNoInternet.gone()
                }
            }
        })
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