package com.gvelesiani.movieapp.domain.pagingDataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gvelesiani.movieapp.domain.models.Movie
import com.gvelesiani.movieapp.domain.useCases.SearchMoviesUseCase
import com.gvelesiani.movieapp.other.extensions.notNull
import kotlinx.coroutines.delay

class SearchMoviesDataSource(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val query: String
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        delay(1500)
        return try {
            val page = params.key ?: 1
            val response = searchMoviesUseCase.run(
                Pair(page, query)
            )
            var movies: List<Movie> = listOf()

            response.body().notNull {
                movies = it.movieResults
            }

            LoadResult.Page(
                data = movies,
                prevKey = null,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}