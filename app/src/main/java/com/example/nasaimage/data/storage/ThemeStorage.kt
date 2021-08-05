package com.example.nasaimage.data.storage

import android.content.Context
import com.example.nasaimage.R
import javax.inject.Inject

class ThemeStorage @Inject constructor( context: Context){
    companion object{
        const val THEME_KEY = "THEME_KEY"
        const val THEME_STORAGE ="THEME_STORAGE"
    }
    private val sharedPreferences = context.getSharedPreferences(THEME_STORAGE, Context.MODE_PRIVATE)
    var theme:Int
        get()= sharedPreferences.getInt("2", R.style.Theme_NasaImage)
        set(theme: Int){sharedPreferences.edit().putInt("2",theme).apply()}
}