package com.gvelesiani.movieapp.modules.diModules

import com.gvelesiani.movieapp.presentation.fragments.movieDetails.MovieDetailsViewModel
import com.gvelesiani.movieapp.presentation.fragments.movieList.MoviesViewModel
import com.gvelesiani.movieapp.presentation.fragments.movieSearch.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        MoviesViewModel(get())
    }

    viewModel {
        MovieDetailsViewModel(get())
    }

    viewModel {
        SearchViewModel(get())
    }
}