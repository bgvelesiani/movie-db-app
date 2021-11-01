package com.gvelesiani.movieapp.domain.useCases

import com.gvelesiani.movieapp.base.BaseUseCase
import com.gvelesiani.movieapp.data.repository.Repository
import com.gvelesiani.movieapp.domain.models.Movie

class SearchMoviesUseCase(private val repository: Repository) :
    BaseUseCase<Pair<Int, String>, List<Movie>>() {
    override suspend fun run(params: Pair<Int, String>): List<Movie> {
        return repository.searchMovies(params.first, params.second)
    }
}