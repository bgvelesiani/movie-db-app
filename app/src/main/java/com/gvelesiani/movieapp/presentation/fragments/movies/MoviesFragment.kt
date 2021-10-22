package com.gvelesiani.movieapp.presentation.fragments.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.gvelesiani.movieapp.R
import com.gvelesiani.movieapp.databinding.FragmentMoviesBinding
import com.gvelesiani.movieapp.other.adapter.MovieListAdapter
import com.gvelesiani.mvvm.extensions.gone
import com.gvelesiani.mvvm.extensions.visible
import com.gvelesiani.mvvm.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : BaseFragment<MoviesViewModel, FragmentMoviesBinding>() {

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val viewModel: MoviesViewModel by viewModel()
    private val recyclerViewAdapter = MovieListAdapter {
        val action =
            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(it)
        findNavController().navigate(action)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoviesBinding
        get() = FragmentMoviesBinding::inflate


    override fun setupView(binding: FragmentMoviesBinding, savedInstanceState: Bundle?) {
        setUpRecyclerViewWithAdapter()
        viewModel.getPopularMovies(1)
        setOnClickListeners()
        //setupSearchView()


        setUpObservers()

    }

//    private fun setupSearchView() {
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                itemAdapter.filter(newText)
//                itemAdapter.itemFilter.filterPredicate =
//                    { item: MovieItem, constraint: CharSequence? ->
//                        item.model.movieTitle.contains(constraint.toString(), ignoreCase = true)
//                    }
//                rvMovies.itemAnimator = null
//                return false
//            }
//        })
//    }

    private fun setUpRecyclerViewWithAdapter() {
        rvMovies.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObservers() {
        viewModel.movieList.observe(this, { list ->
            recyclerViewAdapter.addData(list.movieResults)
            recyclerViewAdapter.notifyDataSetChanged()
        })

        viewModel.loader.observe(this, { showLoader ->
            if (showLoader) {
                progress_bar.visible()
            } else {
                progress_bar.gone()
            }
        })
    }

    private fun setOnClickListeners() {
        btLogOut.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.signInFragment)
            findNavController().popBackStack(R.id.moviesFragment, true)
        }
    }

    override fun provideViewModel(): MoviesViewModel = viewModel
}