package com.gvelesiani.movieapp.presentation.fragments.searchFragment

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gvelesiani.movieapp.base.BaseViewModel
import com.gvelesiani.movieapp.domain.models.Movie
import com.gvelesiani.movieapp.domain.pagingDataSource.SearchMoviesDataSource
import com.gvelesiani.movieapp.domain.useCases.SearchMoviesUseCase
import kotlinx.coroutines.flow.Flow

class SearchViewModel(private val searchMoviesUseCase: SearchMoviesUseCase) : BaseViewModel() {
    fun getSearchedMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = { SearchMoviesDataSource(searchMoviesUseCase, query) }
        ).flow
            .cachedIn(viewModelScope)
    }
}