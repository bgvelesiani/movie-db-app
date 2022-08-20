package com.gvelesiani.movieapp.constants

const val BASE_URL = "https://api.themoviedb.org"
const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

object ApiEndpoints {
    const val POPULAR_MOVIES = "/3/movie/popular"
    const val SIMILAR_MOVIES = "/3/movie/{movie_id}/similar"
    const val SEARCH_MOVIES = "/3/search/movie"
    const val MOVIE_VIDEOS = "/3/movie/{movie_id}/videos"
}