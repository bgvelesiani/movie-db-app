package com.gvelesiani.movieapp.domain.useCases

import com.gvelesiani.movieapp.base.BaseUseCase
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.repository.Repository
import retrofit2.Response

class GetPopularMoviesUseCase(
    private val repository: Repository
) : BaseUseCase<Int, Response<MovieList>>(
) {
    override suspend fun doWork(params: Int): Response<MovieList> {
        return repository.getPopularMovies(params)
    }
}