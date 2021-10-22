package com.gvelesiani.movieapp.domain.useCases

import com.gvelesiani.movieapp.base.BaseUseCase
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response

class GetSimilarMoviesUseCase(
    private val repository: Repository,
    coroutineDispatcher: CoroutineDispatcher
) :
    BaseUseCase<Int, Response<MovieList>>(coroutineDispatcher) {
    override suspend fun buildUseCase(params: Int): Response<MovieList> {
        return repository.getSimilarMovies(params)
    }
}