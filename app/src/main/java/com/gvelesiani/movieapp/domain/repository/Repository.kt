package com.gvelesiani.movieapp.domain.repository

import com.gvelesiani.movieapp.domain.models.MovieList
import com.gvelesiani.movieapp.domain.models.MovieModel
import retrofit2.Response

interface Repository {
    suspend fun getPopularMovies(page: Int): Response<MovieList>
    suspend fun getSimilarMovies(movieId: Int): Response<MovieList>
    suspend fun searchMovies(page: Int, query: String): Response<MovieList>
    suspend fun getMovieTrailer(movieId: Int): Response<MovieModel>
}