package com.example.nasaimage.data.di

import com.example.nasaimage.MainActivity
import com.example.nasaimage.ui.main.NasaImageFragment
import com.example.nasaimage.ui.settings.SettingsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,RepositoryModule::class])
interface AppComponent {

    fun inject(nasaImageFragment:NasaImageFragment)
    fun inject(main: MainActivity)
    fun inject(settingsFragment: SettingsFragment)
}