package com.gvelesiani.movieapp.diModules

import com.gvelesiani.movieapp.domain.useCases.*
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