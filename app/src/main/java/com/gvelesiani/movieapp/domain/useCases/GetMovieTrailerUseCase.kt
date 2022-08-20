package com.gvelesiani.movieapp.domain.useCases

import com.gvelesiani.movieapp.base.BaseUseCase
import com.gvelesiani.movieapp.domain.models.MovieModel
import com.gvelesiani.movieapp.domain.repository.Repository
import retrofit2.Response
import java.io.Serializable

class GetMovieTrailerUseCase(private val repository: Repository): BaseUseCase<Int, Response<MovieModel>>() {
    override suspend fun run(params: Int): Response<MovieModel> {
        return repository.getMovieTrailer(params)
    }
}