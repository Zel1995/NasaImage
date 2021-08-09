package com.example.nasaimage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nasaimage.data.di.App
import com.example.nasaimage.data.storage.ThemeStorage
import com.example.nasaimage.databinding.MainActivityBinding
import com.example.nasaimage.domain.router.MainRouter
import com.example.nasaimage.ui.MainActivityModule
import com.example.nasaimage.ui.MainSubcomponent
import com.example.nasaimage.ui.settings.SettingsFragment
import com.example.nasaimage.ui.settings.SettingsViewModel
import com.example.nasaimage.ui.settings.SettingsViewModelFactory
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    var mainSubcomponent:MainSubcomponent? = null
    @Inject
    lateinit var mainRouter:MainRouter
    var bottomAppBar:BottomAppBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(ThemeStorage(this).theme)
        setContentView(R.layout.main_activity)
        bottomAppBar = findViewById<BottomAppBar>(R.id.bottom_app_bar)
        setSupportActionBar(bottomAppBar)
        mainSubcomponent = (application as App).appComponent.mainComponent().create(MainActivityModule(this))
        mainSubcomponent?.inject(this)
        if (savedInstanceState == null) {
            mainRouter.openNasaFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home ->{
                mainRouter.openNasaFragment()
                return true
            }
            R.id.settings->{
                mainRouter.openSettingsFragment()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}