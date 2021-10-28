package com.gvelesiani.movieapp.other.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.gvelesiani.movieapp.constants.BASE_IMAGE_URL
import com.gvelesiani.movieapp.databinding.MovieItemBinding
import com.gvelesiani.movieapp.domain.models.Movie
import com.gvelesiani.movieapp.other.extensions.loadFromUrl
import com.makeramen.roundedimageview.RoundedImageView
import java.math.RoundingMode
import java.text.DecimalFormat

class SimilarMovieListAdapter(private val clickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<SimilarMovieListAdapter.MyItemViewHolder>() {

    private var movieList = listOf<Movie>()

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

    @SuppressLint("NotifyDataSetChanged")
    fun addData(movies: List<Movie>) {
        movieList = movies
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
        val movie = movieList[position]
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

    override fun getItemCount(): Int {
        return movieList.size
    }
}
