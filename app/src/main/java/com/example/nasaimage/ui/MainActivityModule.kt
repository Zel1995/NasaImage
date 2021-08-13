package com.example.nasaimage.ui

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(val activity: AppCompatActivity) {
    @Provides
    fun provideSupportFragmentManager()= activity.supportFragmentManager
}