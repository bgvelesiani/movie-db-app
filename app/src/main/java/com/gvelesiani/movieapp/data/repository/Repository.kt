package com.gvelesiani.movieapp.data.repository

import com.gvelesiani.movieapp.domain.models.Movie

interface Repository {
    suspend fun getPopularMovies(page: Int): List<Movie>
    suspend fun getSimilarMovies(movieId: Int): List<Movie>
    suspend fun searchMovies(page: Int, query: String): List<Movie>
}