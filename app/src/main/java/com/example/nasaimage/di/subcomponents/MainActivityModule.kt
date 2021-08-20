package com.example.nasaimage.di.subcomponents

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(val activity: AppCompatActivity) {
    @Provides
    fun provideSupportFragmentManager()= activity.supportFragmentManager
}