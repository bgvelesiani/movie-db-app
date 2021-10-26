package com.gvelesiani.movieapp.presentation.fragments.movies

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.domain.models.UIModel
import com.gvelesiani.movieapp.domain.pagingDataSource.MovieDataSource
import com.gvelesiani.movieapp.domain.useCases.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesViewModel(private val getPopularMoviesUseCase: GetPopularMoviesUseCase) :
    BaseViewModel() {

    fun getMovies(): Flow<PagingData<UIModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = { MovieDataSource(getPopularMoviesUseCase) }
        ).flow
            .cachedIn(viewModelScope)
            .map { pagingData ->
                pagingData.map { movieModel ->
                    UIModel.MovieModel(movieModel)
                }
            }
    }

}