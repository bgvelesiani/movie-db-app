package com.gvelesiani.movieapp.presentation.fragments.movieDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gvelesiani.movieapp.base.BaseFragment
import com.gvelesiani.movieapp.common.extensions.gone
import com.gvelesiani.movieapp.common.extensions.loadFromUrl
import com.gvelesiani.movieapp.constants.BASE_IMAGE_URL
import com.gvelesiani.movieapp.databinding.FragmentMovieDetailsBinding
import com.gvelesiani.movieapp.domain.models.Movie
import com.gvelesiani.movieapp.presentation.adapters.SimilarMovieListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MovieDetailsFragment :
    BaseFragment<MovieDetailsViewModel, FragmentMovieDetailsBinding>(MovieDetailsViewModel::class) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate

    private val recyclerViewAdapter = SimilarMovieListAdapter {
        val action =
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(it)
        findNavController().navigate(action)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        requireActivity().bottomNavigationView.gone()
        val movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie
        viewModel.getSimilarMovies(movie.movieId)
        setupMovieDetails(movie)
        setUpObservers()
        setUpRecyclerViewWithAdapter()

        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        binding.btBackClickArea.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setUpRecyclerViewWithAdapter() {
        binding.rvSimilarMovies.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObservers() {
        viewModel.similarMovies.observe(this, { list ->
            recyclerViewAdapter.setData(list)
            recyclerViewAdapter.notifyDataSetChanged()
        })
    }

    private fun setupMovieDetails(movie: Movie) {
        with(binding) {
            tvMovieName.text = movie.movieTitle
            ivMoviePoster.loadFromUrl(BASE_IMAGE_URL + movie.imageUrl)

            tvMovieDescription.text = movie.movieDescription
        }
    }

}