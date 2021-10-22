package com.gvelesiani.movieapp.diModules

import com.gvelesiani.movieapp.presentation.MainViewModel
import com.gvelesiani.movieapp.presentation.fragments.movieDetails.MovieDetailsViewModel
import com.gvelesiani.movieapp.presentation.fragments.movies.MoviesViewModel
import com.gvelesiani.movieapp.presentation.fragments.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        MainViewModel(get())
    }

    viewModel {
        MoviesViewModel(get())
    }

    viewModel {
        MovieDetailsViewModel(get())
    }

    viewModel {
        WelcomeViewModel(get())
    }
}