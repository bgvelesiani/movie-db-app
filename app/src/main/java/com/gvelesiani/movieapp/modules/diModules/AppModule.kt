package com.gvelesiani.movieapp.modules.diModules

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val appModule = module {
    single {
        provideExecutionDispatcher()
    }
}

fun provideExecutionDispatcher() = Dispatchers.IO