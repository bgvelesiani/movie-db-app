package com.gvelesiani.movieapp

import com.gvelesiani.movieapp.base.BaseApplication
import com.gvelesiani.movieapp.diModules.*
import org.koin.core.module.Module

class MovieApp : BaseApplication() {
    override fun provideKoinModules(): List<Module> {
        return listOf(
            viewModelsModule, networkModule, useCasesModule, appModule
        )
    }
}