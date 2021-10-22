package com.gvelesiani.movieapp.presentation.fragments.movies

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.useCases.GetPopularMoviesUseCase
import com.gvelesiani.mvvm.viewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers

class MoviesViewModel(private val getPopularMoviesUseCase: GetPopularMoviesUseCase) :
    BaseViewModel() {

    var loader = MutableLiveData<Boolean>()
    private var movies: MutableLiveData<MovieList> = MutableLiveData()
    val movieList: LiveData<MovieList>
        get() {
            return movies
        }

    fun getPopularMovies(page: Int) {
        loader.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            getPopularMoviesUseCase.execute(this, page, onSuccess = { response ->
                loader.postValue(false)
                movies.value = response.body()!!
            }, onFailure = {
                loader.postValue(false)
                d("onError", it.message.toString())
            })
        }
    }
}