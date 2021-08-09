package com.example.nasaimage

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nasaimage.data.di.App
import com.example.nasaimage.data.storage.ThemeStorage
import com.example.nasaimage.ui.settings.SettingsFragment
import com.example.nasaimage.ui.settings.SettingsViewModel
import com.example.nasaimage.ui.settings.SettingsViewModelFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(ThemeStorage(this).theme)
        setContentView(R.layout.main_activity)
        (application as App).appComponent.inject(this)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment())
                .commitNow()
        }
    }
}