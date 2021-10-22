package com.gvelesiani.movieapp.domain.useCases

import com.gvelesiani.movieapp.base.BaseUseCase
import com.gvelesiani.movieapp.domain.repository.Repository

class UpdateWelcomeScreenButtonStateUseCase(private val repository: Repository) :
    BaseUseCase<Boolean, Unit>() {
    override suspend fun run(params: Boolean) {
        return repository.updateWelcomeScreenButtonState(params)
    }
}