package com.gvelesiani.movieapp.diModules

import com.gvelesiani.movieapp.domain.useCases.GetPopularMoviesUseCase
import com.gvelesiani.movieapp.domain.useCases.GetSimilarMoviesUseCase
import org.koin.dsl.module

val useCasesModule = module {
    factory {
        GetPopularMoviesUseCase(get(), get())
    }

    factory {
        GetSimilarMoviesUseCase(get(), get())
    }
}