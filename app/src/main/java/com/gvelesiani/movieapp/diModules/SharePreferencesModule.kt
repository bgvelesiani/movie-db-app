package com.gvelesiani.movieapp.diModules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

//val sharedPreferencesModule = module {
//    single { provideSharedPref(androidApplication()) }
//}
//
//fun provideSharedPref(app: Application): SharedPreferences {
//    return app.applicationContext.getSharedPreferences(
//        "Moviio_Preferences",
//        Context.MODE_PRIVATE
//    )
//}