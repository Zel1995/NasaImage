package com.example.nasaimage.data.di.subcomponents

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(val activity: AppCompatActivity) {
    @Provides
    fun provideSupportFragmentManager()= activity.supportFragmentManager
}