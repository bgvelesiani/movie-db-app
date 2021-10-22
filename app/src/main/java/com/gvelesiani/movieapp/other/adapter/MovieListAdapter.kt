package com.gvelesiani.movieapp.other.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.gvelesiani.movieapp.R
import com.gvelesiani.movieapp.constants.BASE_IMAGE_URL
import com.gvelesiani.movieapp.domain.models.Movie
import com.gvelesiani.movieapp.other.extensions.loadFromUrl
import com.makeramen.roundedimageview.RoundedImageView
import java.math.RoundingMode
import java.text.DecimalFormat

class MovieListAdapter(private val clickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MyItemViewHolder>() {

    private var movieList: List<Movie> = listOf()

    class MyItemViewHolder(itemView: View, clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val title: AppCompatTextView = itemView.findViewById(R.id.tvMovieName)
        val description: AppCompatTextView = itemView.findViewById(R.id.tvMovieDescription)
        val releaseDate: AppCompatTextView = itemView.findViewById(R.id.tvMovieReleaseDate)
        val poster: RoundedImageView = itemView.findViewById(R.id.ivMoviePoster)
        val rating: AppCompatTextView = itemView.findViewById(R.id.tvRating)

        init {
            itemView.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }
    }

    fun addData(movies: List<Movie>) {
        movieList = movies
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        return MyItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
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

    override fun getItemCount(): Int = movieList.size
}