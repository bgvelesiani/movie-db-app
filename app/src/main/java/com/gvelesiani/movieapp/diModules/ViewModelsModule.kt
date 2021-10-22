package com.gvelesiani.movieapp.diModules

import com.gvelesiani.movieapp.presentation.MainViewModel
import com.gvelesiani.movieapp.presentation.fragments.movieDetails.MovieDetailsViewModel
import com.gvelesiani.movieapp.presentation.fragments.movies.MoviesViewModel
import com.gvelesiani.movieapp.presentation.fragments.signIn.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        MainViewModel()
    }

    viewModel {
        MoviesViewModel(get())
    }

    viewModel {
        MovieDetailsViewModel(get())
    }

    viewModel {
        SignInViewModel()
    }
}