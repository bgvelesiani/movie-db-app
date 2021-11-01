package com.gvelesiani.movieapp.presentation.fragments.movieDetails

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.common.extensions.notNull
import com.gvelesiani.movieapp.domain.models.Movie
import com.gvelesiani.movieapp.domain.useCases.GetSimilarMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase
) :
    BaseViewModel() {

    private var _similarMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val similarMovies: LiveData<List<Movie>>
        get() {
            return _similarMovies
        }

    fun getSimilarMovies(id: Int) {
        viewModelScope.launch {
            try {
                val movies = getSimilarMoviesUseCase.run(id)
                withContext(Dispatchers.Main) {
                    movies.notNull {
                        _similarMovies.value = it
                    }
                }
            } catch (e: Exception) {
                d("onError", e.message.toString())
            }
        }
    }
}