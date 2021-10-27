package com.gvelesiani.movieapp.domain.api

import com.gvelesiani.movieapp.constants.ApiEndpoints
import com.gvelesiani.movieapp.domain.models.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi {
    @GET(ApiEndpoints.POPULAR_MOVIES)
    suspend fun getPopularMovies(@Query("page") page: Int): Response<MovieList>

    @GET(ApiEndpoints.SEARCH_MOVIES)
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<MovieList>

    @GET(ApiEndpoints.SIMILAR_MOVIES)
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Int): Response<MovieList>
}