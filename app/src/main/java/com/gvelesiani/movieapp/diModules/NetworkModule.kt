package com.gvelesiani.movieapp.diModules


import android.content.SharedPreferences
import com.gvelesiani.movieapp.constants.BASE_URL
import com.gvelesiani.movieapp.domain.api.NetworkApi
import com.gvelesiani.movieapp.domain.interceptors.AuthInterceptor
import com.gvelesiani.movieapp.domain.repository.Repository
import com.gvelesiani.movieapp.domain.repository.RepositoryImpl
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideNetworkApi(get()) }
    single { provideRetrofit(get()) }
    single { createRepository(get(), get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
}

fun provideNetworkApi(retrofit: Retrofit): NetworkApi = retrofit.create(NetworkApi::class.java)

fun createRepository(apiService: NetworkApi, sharedPreferences: SharedPreferences): Repository {
    return RepositoryImpl(apiService, sharedPreferences)
}