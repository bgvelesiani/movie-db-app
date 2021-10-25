package com.gvelesiani.movieapp.presentation.fragments.movies

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.useCases.GetPopularMoviesUseCase
import com.gvelesiani.movieapp.other.extensions.notNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel(private val getPopularMoviesUseCase: GetPopularMoviesUseCase) :
    BaseViewModel() {

    var loader = MutableLiveData<Boolean>()
    private var _movies: MutableLiveData<MovieList> = MutableLiveData()
    val movies: LiveData<MovieList>
        get() {
            return _movies
        }

    fun getPopularMovies(id: Int) {
        loader.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getPopularMoviesUseCase.run(id)
                withContext(Dispatchers.Main) {
                    movies.body().notNull {
                        _movies.value = it
                    }
                }
                loader.postValue(false)
            } catch (e: Exception) {
                loader.postValue(false)
                d("onError", e.message.toString())
            }
        }
    }
}