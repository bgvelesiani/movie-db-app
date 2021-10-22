package com.gvelesiani.movieapp.domain.useCases

import com.gvelesiani.movieapp.base.BaseUseCase
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response

class GetPopularMoviesUseCase(
    private val repository: Repository,
    coroutineDispatcher: CoroutineDispatcher
) : BaseUseCase<Response<MovieList>, Int>(
    coroutineDispatcher
) {
    override suspend fun buildUseCase(params: Int): Response<MovieList> {
        return repository.getPopularMovies(params)
    }
}