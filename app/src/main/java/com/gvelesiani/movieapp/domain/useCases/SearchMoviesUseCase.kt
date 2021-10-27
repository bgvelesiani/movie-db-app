package com.gvelesiani.movieapp.domain.useCases

import com.gvelesiani.movieapp.base.BaseUseCase
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.repository.Repository
import retrofit2.Response

class SearchMoviesUseCase(private val repository: Repository) :
    BaseUseCase<Pair<Int, String>, Response<MovieList>>() {
    override suspend fun run(params: Pair<Int, String>): Response<MovieList> {
        return repository.searchMovies(params.first, params.second)
    }
}