package com.example.nasaimage.data.di

import com.example.nasaimage.data.di.subcomponents.MainSubcomponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,RepositoryModule::class])
interface AppComponent {
    fun mainComponent(): MainSubcomponent.Factory
}