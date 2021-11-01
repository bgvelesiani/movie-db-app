package com.gvelesiani.movieapp

import android.app.Application
import com.gvelesiani.movieapp.modules.diModules.appModule
import com.gvelesiani.movieapp.modules.diModules.networkModule
import com.gvelesiani.movieapp.modules.diModules.useCasesModule
import com.gvelesiani.movieapp.modules.diModules.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(provideKoinModules())
        }
    }

    private fun provideKoinModules(): List<Module> {
        return listOf(
            viewModelsModule, networkModule, useCasesModule, appModule
        )
    }
}