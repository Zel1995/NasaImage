package com.example.nasaimage.data.di

import android.app.Application

class App : Application() {
    val appComponent =
        DaggerAppComponent.
        builder().
        build()

}