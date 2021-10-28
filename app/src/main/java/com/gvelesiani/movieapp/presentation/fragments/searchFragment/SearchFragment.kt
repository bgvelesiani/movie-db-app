package com.gvelesiani.movieapp.presentation.fragments.searchFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvelesiani.movieapp.base.BaseFragment
import com.gvelesiani.movieapp.databinding.FragmentSearchBinding
import com.gvelesiani.movieapp.other.adapter.MovieListAdapter
import com.gvelesiani.movieapp.other.adapter.MovieLoadStateAdapter
import com.gvelesiani.movieapp.other.extensions.gone
import com.gvelesiani.movieapp.other.extensions.hideKeyboard
import com.gvelesiani.movieapp.other.extensions.isNetworkAvailable
import com.gvelesiani.movieapp.other.extensions.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {
    private val viewModel: SearchViewModel by viewModel()

    private val recyclerViewAdapter = MovieListAdapter {
        val action =
            SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(it)
        findNavController().navigate(action)
    }

    override fun provideViewModel(): SearchViewModel = viewModel

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        setupRecyclerViewWithAdapter()
        setupSearchView()
        setupListeners()
        checkRecyclerViewItemCount()
    }

    private fun setupListeners() {
        recyclerViewAdapter.addLoadStateListener {
            binding.searchProgressBar.isVisible = it.refresh is LoadState.Loading
        }
    }

    private fun setupRecyclerViewWithAdapter() {
        binding.rvSearchedMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter(requireContext()) { recyclerViewAdapter.retry() })
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.hideKeyboard()

                if (query != null && requireContext().isNetworkAvailable) {
                    binding.tvFindFavourite.gone()
                    binding.ivFragmentSearchIcon.gone()
                    binding.tvNoInternet.gone()

                    viewLifecycleOwner.lifecycleScope.launch {
                        emptyRecyclerView(this)

                        viewModel.getSearchedMovies(query).collectLatest { pagingData ->
                            val data = pagingData.filter {
                                it.movieTitle.contains(query)
                            }
                            recyclerViewAdapter.submitData(data)
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                } else if (query != null && !requireContext().isNetworkAvailable) {
                    emptyRecyclerView(viewLifecycleOwner.lifecycleScope)

                    binding.tvNoInternet.visible()
                    binding.tvFindFavourite.gone()
                    binding.ivFragmentSearchIcon.gone()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        binding.searchView.setOnCloseListener {
            binding.searchView.clearFocus()
            emptyRecyclerView(viewLifecycleOwner.lifecycleScope)

            if (!requireContext().isNetworkAvailable) {
                binding.tvNoInternet.visible()
            } else {
                binding.tvFindFavourite.visible()
                binding.ivFragmentSearchIcon.visible()
            }
            false
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun emptyRecyclerView(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            recyclerViewAdapter.submitData(PagingData.empty())
            recyclerViewAdapter.notifyDataSetChanged()
        }
    }

    private fun checkRecyclerViewItemCount() {
        if (recyclerViewAdapter.itemCount > 0) {
            binding.tvFindFavourite.gone()
            binding.ivFragmentSearchIcon.gone()
        }
    }
}