package com.gvelesiani.movieapp.presentation.fragments.searchFragment

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
import com.gvelesiani.movieapp.other.extensions.visible
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

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.tvFindFavourite.gone()
                    viewLifecycleOwner.lifecycleScope.launch {
                        recyclerViewAdapter.submitData(PagingData.empty())

                        viewModel.getSearchedMovies(query).collectLatest { pagingData ->
                            val data = pagingData.filter {
                                it.movieTitle.contains(query)
                            }
                            recyclerViewAdapter.submitData(data)
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        binding.searchView.setOnCloseListener {
            binding.searchView.clearFocus()
//            viewLifecycleOwner.lifecycleScope.launch {
//                recyclerViewAdapter.submitData(PagingData.empty())
//            }
            binding.tvFindFavourite.visible()
            false
        }
    }
}