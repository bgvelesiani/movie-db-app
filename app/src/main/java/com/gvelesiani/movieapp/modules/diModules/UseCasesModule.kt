package com.gvelesiani.movieapp.modules.diModules

import com.gvelesiani.movieapp.domain.useCases.GetPopularMoviesUseCase
import com.gvelesiani.movieapp.domain.useCases.GetSimilarMoviesUseCase
import com.gvelesiani.movieapp.domain.useCases.SearchMoviesUseCase
import org.koin.dsl.module

val useCasesModule = module {
    factory {
        GetPopularMoviesUseCase(get())
    }

    factory {
        GetSimilarMoviesUseCase(get())
    }

    factory {
        SearchMoviesUseCase(get())
    }
}