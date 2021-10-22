package com.gvelesiani.movieapp.diModules

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {
    single {
        provideExecutionDispatcher()
    }
}

fun provideExecutionDispatcher() = Dispatchers.IO