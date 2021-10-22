package com.gvelesiani.movieapp.presentation.fragments.movieDetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.gvelesiani.movieapp.R
import com.gvelesiani.movieapp.base.BaseFragment
import com.gvelesiani.movieapp.constants.BASE_IMAGE_URL
import com.gvelesiani.movieapp.databinding.FragmentMovieDetailsBinding
import com.gvelesiani.movieapp.domain.models.Movie
import com.gvelesiani.movieapp.other.adapter.MovieListAdapter
import com.gvelesiani.movieapp.other.extensions.gone
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel, FragmentMovieDetailsBinding>() {

    private val viewModel: MovieDetailsViewModel by viewModel()

    lateinit var auth: FirebaseAuth

    private val recyclerViewAdapter = MovieListAdapter {
        val action =
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(it)
        findNavController().navigate(action)
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate


    override fun setupView(binding: FragmentMovieDetailsBinding, savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        val movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie
        viewModel.getSimilarMovies(movie.movieId)
        setupMovieDetails(movie)
        setUpRecyclerViewWithAdapter()
        setUpObservers()

        if (auth.currentUser == null) {
            btFavorite.gone()
        }

        binding.btBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btFavorite.setOnClickListener {
            it.setBackgroundResource(R.drawable.ic_favorite)
        }
    }

    private fun setUpRecyclerViewWithAdapter() {
        rvSimilarMovies.apply {
            adapter = recyclerViewAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObservers() {
        viewModel.similarMoviesList.observe(this, { list ->
            recyclerViewAdapter.addData(list.movieResults)
            recyclerViewAdapter.notifyDataSetChanged()
        })
    }

    private fun setupMovieDetails(movie: Movie) {
        tvMovieName.text = movie.movieTitle
        Glide.with(requireContext()).load(BASE_IMAGE_URL + movie.imageUrl)
            .into(ivMoviePoster)

        tvMovieDescription.text = movie.movieDescription
    }

    override fun provideViewModel(): MovieDetailsViewModel = viewModel

}