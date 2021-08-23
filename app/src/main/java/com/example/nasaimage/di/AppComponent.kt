package com.example.nasaimage.di

import com.example.nasaimage.di.subcomponents.MainSubcomponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,RepositoryModule::class,DatabaseModule::class])
interface AppComponent {
    fun mainComponent(): MainSubcomponent.Factory
}