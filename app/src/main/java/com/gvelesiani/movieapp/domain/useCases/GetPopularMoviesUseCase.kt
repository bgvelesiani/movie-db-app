package com.gvelesiani.movieapp.domain.useCases

import com.gvelesiani.movieapp.base.BaseUseCase
import com.gvelesiani.movieapp.data.repository.Repository
import com.gvelesiani.movieapp.domain.models.Movie

class GetPopularMoviesUseCase(
    private val repository: Repository
) : BaseUseCase<Int, List<Movie>>() {
    override suspend fun run(params: Int): List<Movie> {
        return repository.getPopularMovies(params)
    }
}