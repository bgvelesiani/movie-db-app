package com.gvelesiani.movieapp.presentation.fragments.movieDetails

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.models.MovieModel
import com.gvelesiani.movieapp.domain.useCases.GetMovieTrailerUseCase
import com.gvelesiani.movieapp.domain.useCases.GetSimilarMoviesUseCase
import com.gvelesiani.movieapp.other.extensions.notNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getMovieTrailerUseCase: GetMovieTrailerUseCase
) :
    BaseViewModel() {

    private var _similarMovies: MutableLiveData<MovieList> = MutableLiveData()
    val similarMovies: LiveData<MovieList>
        get() {
            return _similarMovies
        }

    private var _movieVideos: MutableLiveData<MovieModel> = MutableLiveData()
    val movieVideos: LiveData<MovieModel>
        get() {
            return _movieVideos
        }

    fun getSimilarMovies(id: Int) {
        viewModelScope.launch {
            try {
                val movies = getSimilarMoviesUseCase.run(id)
                withContext(Dispatchers.Main) {
                    movies.body().notNull {
                        _similarMovies.value = it
                    }
                }
            } catch (e: Exception) {
                d("onError", e.message.toString())
            }
        }
    }

    fun getMovieVideos(movieId: Int) {
        viewModelScope.launch {
            try {
                val videos = getMovieTrailerUseCase.run(movieId)
                _movieVideos.value = videos.body()
            } catch (e: Exception) {}
        }
    }
}