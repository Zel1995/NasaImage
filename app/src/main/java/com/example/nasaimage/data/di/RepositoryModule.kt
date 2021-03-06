package com.example.nasaimage.data.di

import com.example.nasaimage.BuildConfig
import com.example.nasaimage.data.network.NasaApi
import com.example.nasaimage.data.repository.RepositoryImpl
import com.example.nasaimage.domain.repository.Repository
import com.example.nasaimage.domain.usecase.NasaDataInteractor
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(nasaApi: NasaApi): Repository = RepositoryImpl(nasaApi)

    @Provides
    fun providesNasaApi(): NasaApi {
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NasaApi::class.java)
    }
}