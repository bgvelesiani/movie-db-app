package com.gvelesiani.movieapp.domain.repository

import com.gvelesiani.movieapp.domain.api.NetworkApi
import com.gvelesiani.movieapp.domain.models.MovieList
import retrofit2.Response

class RepositoryImpl(private val networkApi: NetworkApi) : Repository {
    override suspend fun getPopularMovies(page: Int): Response<MovieList> {
        return networkApi.getPopularMovies(page)
    }

    override suspend fun getSimilarMovies(movieId: Int): Response<MovieList> {
        return networkApi.getSimilarMovies(movieId)
    }
}