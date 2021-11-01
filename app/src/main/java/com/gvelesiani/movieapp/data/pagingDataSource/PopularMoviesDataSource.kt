package com.gvelesiani.movieapp.data.pagingDataSource

import android.util.Log.d
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gvelesiani.movieapp.common.extensions.notNull
import com.gvelesiani.movieapp.domain.models.Movie
import com.gvelesiani.movieapp.domain.useCases.GetPopularMoviesUseCase

class PopularMoviesDataSource(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = getPopularMoviesUseCase.run(page)
            var movies: List<Movie> = listOf()

            response.notNull {
                movies = it
            }

            LoadResult.Page(
                data = movies,
                prevKey = null,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            d("onError", e.message.toString())
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