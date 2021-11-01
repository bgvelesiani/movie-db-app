package com.gvelesiani.movieapp.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.gvelesiani.movieapp.common.extensions.loadFromUrl
import com.gvelesiani.movieapp.constants.BASE_IMAGE_URL
import com.gvelesiani.movieapp.databinding.MovieItemBinding
import com.gvelesiani.movieapp.domain.models.Movie
import com.makeramen.roundedimageview.RoundedImageView
import java.math.RoundingMode
import java.text.DecimalFormat

class SimilarMovieListAdapter(private val clickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<SimilarMovieListAdapter.MyItemViewHolder>() {

    private var movieList = listOf<Movie>()

    class MyItemViewHolder(binding: MovieItemBinding, clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        private val title: AppCompatTextView = binding.tvMovieName
        private val description: AppCompatTextView = binding.tvMovieDescription
        private val releaseDate: AppCompatTextView = binding.tvMovieReleaseDate
        private val poster: RoundedImageView = binding.ivMoviePoster
        private val rating: AppCompatTextView = binding.tvRating

        fun bindMovie(movie: Movie) {
            title.text = movie.movieTitle
            description.text = movie.movieDescription
            releaseDate.text = movie.movieReleaseDate
            poster.loadFromUrl("$BASE_IMAGE_URL${movie.imageUrl}")

            //
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING

            rating.text = if (movie.movieRating.toString().length > 3) {
                df.format(movie.movieRating).toString()
            } else {
                movie.movieRating.toString()
            }
        }

        init {
            binding.root.setOnClickListener {
                clickAtPosition(bindingAdapterPosition)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Movie>) {
        movieList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        return MyItemViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ) { position ->
            clickListener(movieList[position])
        }
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        holder.bindMovie(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}
