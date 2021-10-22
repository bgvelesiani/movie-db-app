package com.gvelesiani.movieapp.constants

const val API_KEY = "87718062a37e87abcf8b43193158ffc7"
const val BASE_URL = "https://api.themoviedb.org"
const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

object ApiEndpoints {
    const val POPULAR_MOVIES = "/3/movie/popular"
    const val SIMILAR_MOVIES = "/3/movie/{movie_id}/similar"
}