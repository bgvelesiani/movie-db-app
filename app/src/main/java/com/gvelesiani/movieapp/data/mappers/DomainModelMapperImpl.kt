package com.gvelesiani.movieapp.data.mappers

import com.gvelesiani.movieapp.data.models.MovieDto
import com.gvelesiani.movieapp.data.models.MovieList
import com.gvelesiani.movieapp.domain.models.Movie

class DomainModelMapperImpl : DomainModelMapper<MovieDto, Movie> {
    override fun mapToDomainModel(dtoModel: MovieDto): Movie {
        return Movie(
            dtoModel.isAdultRated,
            dtoModel.imageUrl,
            dtoModel.movieId,
            dtoModel.movieTitle,
            dtoModel.movieRating,
            dtoModel.movieReleaseDate,
            dtoModel.movieDescription,
            dtoModel.movieVoteCount,
            dtoModel.movieStatus
        )
    }


    fun toDomainList(dto: MovieList): List<Movie> {
        return dto.movieDtoResults.map {
            mapToDomainModel(it)
        }
    }
}