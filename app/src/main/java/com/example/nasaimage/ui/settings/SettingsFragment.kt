package com.example.nasaimage.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasaimage.R
import com.example.nasaimage.data.di.App
import com.example.nasaimage.data.storage.ThemeStorage
import com.example.nasaimage.databinding.FragmentSettingsBinding
import com.example.nasaimage.viewBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var themeStorage: ThemeStorage

    private val viewBinding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        themeStorage = ThemeStorage(requireContext())
        when (themeStorage.theme) {
            R.style.Theme_NasaImage -> {
                viewBinding.defaultRb.isChecked = true
            }
            R.style.Theme_NasaImage_Blue -> {
                viewBinding.blueRb.isChecked = true
            }
        }
        viewBinding.defaultRb.setOnClickListener {
            setMyTheme(R.style.Theme_NasaImage)
        }
        viewBinding.blueRb.setOnClickListener {
            setMyTheme(R.style.Theme_NasaImage_Blue)
        }
    }
    private fun setMyTheme(theme:Int){
        themeStorage.theme = theme
        requireActivity().recreate()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as App).appComponent.inject(this)
    }


}

class SettingsViewModelFactory:
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel() as T
    }
}