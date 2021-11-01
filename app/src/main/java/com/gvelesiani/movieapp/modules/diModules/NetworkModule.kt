package com.gvelesiani.movieapp.modules.diModules


import com.gvelesiani.movieapp.constants.BASE_URL
import com.gvelesiani.movieapp.data.api.NetworkApi
import com.gvelesiani.movieapp.data.interceptors.AuthInterceptor
import com.gvelesiani.movieapp.data.mappers.DomainModelMapperImpl
import com.gvelesiani.movieapp.data.repository.Repository
import com.gvelesiani.movieapp.data.repository.RepositoryImpl
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { AuthInterceptor() }
    factory { provideOkHttpClient(get()) }
    factory { provideNetworkApi(get()) }
    single { provideRetrofit(get()) }
    single { provideDomainMapper() }
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

fun createRepository(apiService: NetworkApi, domainMapper: DomainModelMapperImpl): Repository {
    return RepositoryImpl(apiService, domainMapper)
}

fun provideDomainMapper(): DomainModelMapperImpl {
    return DomainModelMapperImpl()
}