package com.example.nasaimage.ui.router

import androidx.fragment.app.FragmentManager
import com.example.nasaimage.R
import com.example.nasaimage.ui.main.NasaImageFragment
import com.example.nasaimage.ui.note.NotesFragment
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

    fun openNoteFragment(){
        supportFragmentManager.beginTransaction().replace(R.id.container, NotesFragment())
            .commit()
    }

}