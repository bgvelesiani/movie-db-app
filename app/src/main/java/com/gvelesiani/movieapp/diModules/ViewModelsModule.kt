package com.gvelesiani.movieapp.diModules

import com.gvelesiani.movieapp.presentation.MainViewModel
import com.gvelesiani.movieapp.presentation.fragments.movieDetails.MovieDetailsViewModel
import com.gvelesiani.movieapp.presentation.fragments.movieList.MoviesViewModel
import com.gvelesiani.movieapp.presentation.fragments.movieSearch.SearchViewModel
import com.gvelesiani.movieapp.presentation.welcome.WelcomeViewModel
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
        MovieDetailsViewModel(get(), get())
    }

    viewModel {
        WelcomeViewModel()
    }

    viewModel {
        SearchViewModel(get())
    }
}