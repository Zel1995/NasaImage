package com.example.nasaimage.ui

import com.example.nasaimage.MainActivity
import com.example.nasaimage.domain.router.RouterModule
import com.example.nasaimage.ui.main.NasaImageFragment
import com.example.nasaimage.ui.main.subfragments.BeforeYesterdayFragment
import com.example.nasaimage.ui.main.subfragments.TodayFragment
import com.example.nasaimage.ui.main.subfragments.YesterdayFragment
import com.example.nasaimage.ui.settings.SettingsFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class,RouterModule::class])
interface MainSubcomponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(mainActivityModule: MainActivityModule):MainSubcomponent
    }

    fun inject(nasaImageFragment: NasaImageFragment)
    fun inject(main: MainActivity)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(beforeYesterdayFragment: BeforeYesterdayFragment)
    fun inject(todayFragment: TodayFragment)
    fun inject(yesterdayFragment: YesterdayFragment)
}