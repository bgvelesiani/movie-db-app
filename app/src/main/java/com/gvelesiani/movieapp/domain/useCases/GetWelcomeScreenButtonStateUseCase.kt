package com.gvelesiani.movieapp.domain.useCases

import com.gvelesiani.movieapp.base.BaseUseCase
import com.gvelesiani.movieapp.domain.repository.Repository

class GetWelcomeScreenButtonStateUseCase(private val repository: Repository): BaseUseCase<Unit, Boolean>() {
    override suspend fun doWork(params: Unit): Boolean {
        return repository.getWelcomeScreenButtonState()
    }
}