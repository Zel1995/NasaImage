package com.example.nasaimage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nasaimage.data.di.App
import com.example.nasaimage.data.storage.ThemeStorage
import com.example.nasaimage.ui.router.MainRouter
import com.example.nasaimage.data.di.subcomponents.MainActivityModule
import com.example.nasaimage.data.di.subcomponents.MainSubcomponent
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    var mainSubcomponent: MainSubcomponent? = null

    @Inject
    lateinit var mainRouter: MainRouter
    var bottomAppBar: BottomAppBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(ThemeStorage(this).theme)
        setContentView(R.layout.main_activity)
        mainSubcomponent =
            (application as App).appComponent.mainComponent().create(MainActivityModule(this))
        mainSubcomponent?.inject(this)
        if (savedInstanceState == null) {
            mainRouter.openNasaFragment()
        }
        initNavigation()
    }

    private fun initNavigation() {
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    mainRouter.openNasaFragment()
                    true
                }
                R.id.settings -> {
                    mainRouter.openSettingsFragment()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }


}