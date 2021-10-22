package com.gvelesiani.movieapp.diModules

import com.gvelesiani.movieapp.domain.useCases.GetPopularMoviesUseCase
import com.gvelesiani.movieapp.domain.useCases.GetSimilarMoviesUseCase
import com.gvelesiani.movieapp.domain.useCases.GetWelcomeScreenButtonStateUseCase
import com.gvelesiani.movieapp.domain.useCases.UpdateWelcomeScreenButtonStateUseCase
import org.koin.dsl.module

val useCasesModule = module {
    factory {
        GetPopularMoviesUseCase(get())
    }

    factory {
        GetSimilarMoviesUseCase(get())
    }

    factory {
        GetWelcomeScreenButtonStateUseCase(get())
    }

    factory {
        UpdateWelcomeScreenButtonStateUseCase(get())
    }
}