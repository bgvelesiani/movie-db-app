package com.gvelesiani.movieapp.presentation.fragments.movies

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.useCases.GetPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MoviesViewModel(private val getPopularMoviesUseCase: GetPopularMoviesUseCase) :
    BaseViewModel() {

    var loader = MutableLiveData<Boolean>()
    private var _movies: MutableLiveData<MovieList> = MutableLiveData()
    val movies: LiveData<MovieList>
        get() {
            return _movies
        }

//    fun getPopularMovies(page: Int) {
//        loader.postValue(true)
//        getPopularMoviesUseCase.execute(viewModelScope, page, onSuccess = { response ->
//            loader.postValue(false)
//            _movies.value = response.body()!!
//        }, onFailure = {
//            loader.postValue(false)
//            d("onError", it.message.toString())
//        })
//    }

    fun getPopularMovies(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getPopularMoviesUseCase.doWork(id)
                withContext(Dispatchers.Main){
                    _movies.value = movies.body()
                }
            } catch (e: Exception){
                d("onError", e.message.toString())
            }
        }
    }
}