package com.example.nasaimage.data.di

import com.example.nasaimage.MainActivity
import com.example.nasaimage.ui.main.NasaImageFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,RepositoryModule::class])
interface AppComponent {

    fun inject(main:NasaImageFragment)
    fun inject(main: MainActivity)
}