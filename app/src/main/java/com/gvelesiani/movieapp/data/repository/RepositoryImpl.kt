package com.gvelesiani.movieapp.data.repository

import com.gvelesiani.movieapp.data.api.NetworkApi
import com.gvelesiani.movieapp.data.mappers.DomainModelMapperImpl
import com.gvelesiani.movieapp.domain.models.Movie

class RepositoryImpl(
    private val networkApi: NetworkApi,
    private val domainMapper: DomainModelMapperImpl
) : Repository {
    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return domainMapper.toDomainList(networkApi.getPopularMovies(page))
    }

    override suspend fun searchMovies(page: Int, query: String): List<Movie> {
        return domainMapper.toDomainList(networkApi.searchMovies(page, query))
    }

    override suspend fun getSimilarMovies(movieId: Int): List<Movie> {
        return domainMapper.toDomainList(networkApi.getSimilarMovies(movieId))
    }
}