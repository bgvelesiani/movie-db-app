package com.gvelesiani.movieapp.diModules

import com.gvelesiani.movieapp.presentation.MainViewModel
import com.gvelesiani.movieapp.presentation.fragments.movieDetails.MovieDetailsViewModel
import com.gvelesiani.movieapp.presentation.fragments.movies.MoviesViewModel
import com.gvelesiani.movieapp.presentation.fragments.searchFragment.SearchViewModel
import com.gvelesiani.movieapp.presentation.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        MainViewModel(get())
    }

    viewModel {
        MoviesViewModel(get(), get())
    }

    viewModel {
        MovieDetailsViewModel(get(), get())
    }

    viewModel {
        WelcomeViewModel(get())
    }

    viewModel {
        SearchViewModel(get(), get())
    }
}