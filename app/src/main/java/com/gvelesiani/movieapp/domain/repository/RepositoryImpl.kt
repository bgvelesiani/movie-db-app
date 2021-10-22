package com.gvelesiani.movieapp.domain.repository

import android.content.SharedPreferences
import com.gvelesiani.movieapp.domain.api.NetworkApi
import com.gvelesiani.movieapp.domain.models.MovieList
import retrofit2.Response

class RepositoryImpl(private val networkApi: NetworkApi, private val sharedPreferences: SharedPreferences) : Repository {
    override suspend fun getPopularMovies(page: Int): Response<MovieList> {
        return networkApi.getPopularMovies(page)
    }

    override suspend fun getSimilarMovies(movieId: Int): Response<MovieList> {
        return networkApi.getSimilarMovies(movieId)
    }

    override suspend fun getWelcomeScreenButtonState(): Boolean {
        return sharedPreferences.getBoolean("isWelcomeScreenButtonClicked", false)
    }

    override suspend fun updateWelcomeScreenButtonState(isClicked: Boolean) {
        sharedPreferences.edit().putBoolean("isWelcomeScreenButtonClicked", true).apply()
    }
}