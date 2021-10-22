package com.gvelesiani.movieapp.presentation.fragments.movieDetails

import android.util.Log.d
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.useCases.GetSimilarMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MovieDetailsViewModel(private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase) :
    BaseViewModel() {

    //var loader = MutableLiveData<Boolean>()
    private var _similarMovies: MutableLiveData<MovieList> = MutableLiveData()
    val similarMovies: LiveData<MovieList>
        get() {
            return _similarMovies
        }

    fun getSimilarMovies(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getSimilarMoviesUseCase.doWork(id)
                withContext(Dispatchers.Main){
                    _similarMovies.value = movies.body()
                }
            } catch (e: Exception){
                d("onError", e.message.toString())
            }
        }
    }
}