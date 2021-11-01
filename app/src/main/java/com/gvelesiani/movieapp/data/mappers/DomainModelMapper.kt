package com.gvelesiani.movieapp.data.mappers

interface DomainModelMapper<T, DomainModel> {
    fun mapToDomainModel(dtoModel: T): DomainModel
}