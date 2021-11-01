package com.gvelesiani.movieapp.presentation.fragments.movieSearch

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
import com.gvelesiani.movieapp.common.extensions.gone
import com.gvelesiani.movieapp.common.extensions.hideKeyboard
import com.gvelesiani.movieapp.common.extensions.isNetworkAvailable
import com.gvelesiani.movieapp.common.extensions.visible
import com.gvelesiani.movieapp.databinding.FragmentSearchBinding
import com.gvelesiani.movieapp.presentation.adapters.MovieListAdapter
import com.gvelesiani.movieapp.presentation.adapters.MovieLoadStateAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment :
    BaseFragment<SearchViewModel, FragmentSearchBinding>(SearchViewModel::class) {

    private val recyclerViewAdapter = MovieListAdapter {
        val action =
            SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(it)
        findNavController().navigate(action)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun setupView(savedInstanceState: Bundle?) {
        requireActivity().bottomNavigationView.visible()
        setupRecyclerViewWithAdapter()
        setupSearchView()
        checkRecyclerViewItemCount()
    }

    private fun setupRecyclerViewWithAdapter() {
        binding.rvSearchedMovies.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter(requireContext()) { recyclerViewAdapter.retry() })
        }
        recyclerViewAdapter.addLoadStateListener {
            binding.searchProgressBar.isVisible = it.refresh is LoadState.Loading
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupSearchView() {
        val isNetworkAvailable = requireContext().isNetworkAvailable
        val searchView = binding.searchView
        searchView.setOnClickListener {
            searchView.onActionViewExpanded()
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.hideKeyboard()

                if (query != null && isNetworkAvailable) {
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
                } else if (query != null && !isNetworkAvailable) {
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

        searchView.setOnCloseListener {
            searchView.clearFocus()
            emptyRecyclerView(viewLifecycleOwner.lifecycleScope)

            if (!isNetworkAvailable) {
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