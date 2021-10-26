package com.gvelesiani.movieapp.other.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gvelesiani.movieapp.constants.BASE_IMAGE_URL
import com.gvelesiani.movieapp.databinding.MovieItemBinding
import com.gvelesiani.movieapp.domain.models.Movie
import com.gvelesiani.movieapp.domain.models.UIModel
import com.gvelesiani.movieapp.other.extensions.loadFromUrl
import com.makeramen.roundedimageview.RoundedImageView
import java.math.RoundingMode
import java.text.DecimalFormat

class MovieListAdapter(private val clickListener: (Movie) -> Unit) :
    PagingDataAdapter<UIModel, MovieListAdapter.MyItemViewHolder>(MOVIE_COMPARATOR) {

    class MyItemViewHolder(binding: MovieItemBinding, clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        val title: AppCompatTextView = binding.tvMovieName
        val description: AppCompatTextView = binding.tvMovieDescription
        val releaseDate: AppCompatTextView = binding.tvMovieReleaseDate
        val poster: RoundedImageView = binding.ivMoviePoster
        val rating: AppCompatTextView = binding.tvRating

        init {
            binding.root.setOnClickListener {
                clickAtPosition(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        return MyItemViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ) { position ->
            val uiModel = getItem(position)
            uiModel.let {
                when (uiModel) {
                    is UIModel.MovieModel -> clickListener(uiModel.model)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        fun setupMovie(movie: Movie) {
            holder.title.text = movie.movieTitle
            holder.description.text = movie.movieDescription
            holder.releaseDate.text = movie.movieReleaseDate
            holder.poster.loadFromUrl("$BASE_IMAGE_URL${movie.imageUrl}")

            //
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING

            holder.rating.text = if (movie.movieRating.toString().length > 3) {
                df.format(movie.movieRating).toString()
            } else {
                movie.movieRating.toString()
            }
        }

        val uiModel = getItem(position)
        uiModel.let {
            when (uiModel) {
                is UIModel.MovieModel -> setupMovie(uiModel.model)
            }
        }
    }


    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<UIModel>() {
            override fun areItemsTheSame(oldItem: UIModel, newItem: UIModel): Boolean {
                return (oldItem is UIModel.MovieModel && newItem is UIModel.MovieModel && oldItem.model.movieTitle == newItem.model.movieTitle)
            }

            override fun areContentsTheSame(oldItem: UIModel, newItem: UIModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}