package com.gvelesiani.movieapp.presentation.fragments.movieDetails

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.useCases.GetSimilarMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase) :
    BaseViewModel() {

    var loader = MutableLiveData<Boolean>()
    private var similarMovies: MutableLiveData<MovieList> = MutableLiveData()
    val similarMoviesList: LiveData<MovieList>
        get() {
            return similarMovies
        }

    fun getSimilarMovies(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getSimilarMoviesUseCase.doWork(id)
                withContext(Dispatchers.Main) {
                    similarMovies.value = result.body()
                }
            } catch (e: Exception) {
                d("onErorr", e.message.toString())
            }
        }
    }
}