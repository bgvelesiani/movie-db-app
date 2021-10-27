package com.gvelesiani.movieapp.presentation.fragments.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gvelesiani.movieapp.base.BaseFragment
import com.gvelesiani.movieapp.constants.BASE_IMAGE_URL
import com.gvelesiani.movieapp.databinding.FragmentMovieDetailsBinding
import com.gvelesiani.movieapp.domain.models.Movie
import com.gvelesiani.movieapp.other.adapter.SimilarMovieListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel, FragmentMovieDetailsBinding>() {

    private val viewModel: MovieDetailsViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate

    private val recyclerViewAdapter = SimilarMovieListAdapter {
        val action =
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(it)
        findNavController().navigate(action)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        val movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie
        viewModel.getSimilarMovies(movie.movieId)
        setupMovieDetails(movie)
        setUpRecyclerViewWithAdapter()
        setUpObservers()
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
            layoutManager =
                LinearLayoutManager(requireContext())
        }
    }

    private fun setUpObservers() {
        viewModel.similarMovies.observe(this, { list ->
            recyclerViewAdapter.addData(list.movieResults)
        })
    }

    private fun setupMovieDetails(movie: Movie) {
        with(binding) {
            tvMovieName.text = movie.movieTitle
            Glide.with(requireContext()).load(BASE_IMAGE_URL + movie.imageUrl)
                .into(ivMoviePoster)

            tvMovieDescription.text = movie.movieDescription
        }
    }

    override fun provideViewModel(): MovieDetailsViewModel = viewModel

}