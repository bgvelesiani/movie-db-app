package com.gvelesiani.movieapp.presentation.fragments.movies

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.domain.ConnectionLiveData
import com.gvelesiani.movieapp.domain.models.Movie
import com.gvelesiani.movieapp.domain.pagingDataSource.PopularMoviesDataSource
import com.gvelesiani.movieapp.domain.useCases.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow

class MoviesViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    application: Application
) :
    BaseViewModel(application) {
    val connectionLiveData: ConnectionLiveData = ConnectionLiveData(application)

    fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = { PopularMoviesDataSource(getPopularMoviesUseCase) }
        ).flow
            .cachedIn(viewModelScope)
    }
}