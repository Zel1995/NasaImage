package com.example.nasaimage.data.di

import com.example.nasaimage.MainActivity
import com.example.nasaimage.ui.MainSubcomponent
import com.example.nasaimage.ui.main.NasaImageFragment
import com.example.nasaimage.ui.settings.SettingsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,RepositoryModule::class])
interface AppComponent {
    fun mainComponent():MainSubcomponent.Factory
}