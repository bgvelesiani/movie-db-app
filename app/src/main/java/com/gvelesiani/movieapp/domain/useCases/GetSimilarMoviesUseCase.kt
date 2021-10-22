package com.gvelesiani.movieapp.domain.useCases

import com.gvelesiani.movieapp.base.BaseUseCase
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.repository.Repository
import retrofit2.Response

class GetSimilarMoviesUseCase(
    private val repository: Repository
) :
    BaseUseCase<Int, Response<MovieList>>() {
    override suspend fun run(params: Int): Response<MovieList> {
        return repository.getSimilarMovies(params)
    }
}