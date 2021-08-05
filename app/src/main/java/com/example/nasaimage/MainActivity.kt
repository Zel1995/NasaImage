package com.example.nasaimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasaimage.data.di.App
import com.example.nasaimage.ui.main.NasaImageFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NasaImageFragment())
                .commitNow()
        }

        (application as? App)?.appComponent?.inject(this)
    }
}