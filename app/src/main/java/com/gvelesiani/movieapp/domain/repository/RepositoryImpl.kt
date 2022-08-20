package com.gvelesiani.movieapp.domain.repository

import com.gvelesiani.movieapp.domain.api.NetworkApi
import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.models.MovieModel
import retrofit2.Response

class RepositoryImpl(
    private val networkApi: NetworkApi
) : Repository {
    override suspend fun getPopularMovies(page: Int): Response<MovieList> {
        return networkApi.getPopularMovies(page)
    }

    override suspend fun searchMovies(page: Int, query: String): Response<MovieList> {
        return networkApi.searchMovies(page, query)
    }

    override suspend fun getSimilarMovies(movieId: Int): Response<MovieList> {
        return networkApi.getSimilarMovies(movieId)
    }

    override suspend fun getMovieTrailer(movieId: Int): Response<MovieModel> {
        return networkApi.getMovieTrailer(movieId)
    }
}