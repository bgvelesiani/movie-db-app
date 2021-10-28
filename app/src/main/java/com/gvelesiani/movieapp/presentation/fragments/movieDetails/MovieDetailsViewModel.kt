package com.gvelesiani.movieapp.presentation.fragments.movieDetails

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.useCases.GetSimilarMoviesUseCase
import com.gvelesiani.movieapp.other.extensions.notNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase
) :
    BaseViewModel() {

    //var loader = MutableLiveData<Boolean>()
    private var _similarMovies: MutableLiveData<MovieList> = MutableLiveData()
    val similarMovies: LiveData<MovieList>
        get() {
            return _similarMovies
        }

    fun getSimilarMovies(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
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
}