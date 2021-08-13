package com.example.nasaimage.domain.router

import androidx.fragment.app.FragmentManager
import com.example.nasaimage.R
import com.example.nasaimage.ui.main.NasaImageFragment
import com.example.nasaimage.ui.settings.SettingsFragment

class MainRouter(private val supportFragmentManager: FragmentManager) {
    fun openNasaFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container, NasaImageFragment())
            .commit()
    }
    fun openSettingsFragment(){
        supportFragmentManager.beginTransaction().replace(R.id.container, SettingsFragment())
            .commit()
    }
}