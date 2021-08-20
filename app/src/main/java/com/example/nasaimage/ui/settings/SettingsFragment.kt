package com.example.nasaimage.ui.settings

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasaimage.MainActivity
import com.example.nasaimage.R
import com.example.nasaimage.domain.storage.ThemeStorage
import com.example.nasaimage.databinding.FragmentSettingsStartBinding
import com.example.nasaimage.viewBinding

class SettingsFragment : Fragment(R.layout.fragment_settings_start) {
    private lateinit var themeStorage: ThemeStorage

    private val viewBinding: FragmentSettingsStartBinding by viewBinding(
        FragmentSettingsStartBinding::bind
    )
    private var showFabs = false
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
        initFabs()
    }

    private fun initFabs() {
        with(viewBinding) {

            fab.setOnClickListener {
                val constraintSet = ConstraintSet()
                val changeBounds = ChangeBounds().apply {
                    interpolator = AnticipateOvershootInterpolator()
                    duration = 1000
                }
                TransitionManager.beginDelayedTransition(viewBinding.settingsConstraint,changeBounds)

                if (!showFabs) {
                    ObjectAnimator.ofFloat(viewBinding.fab,"rotation", 0f,180f).start()
                    constraintSet.clone(requireContext(), R.layout.fragment_settings)
                    constraintSet.applyTo(viewBinding.settingsConstraint)
                } else {
                    ObjectAnimator.ofFloat(viewBinding.fab,"rotation", 180f,0f).start()
                    constraintSet.clone(requireContext(), R.layout.fragment_settings_start)
                    constraintSet.applyTo(viewBinding.settingsConstraint)
                }
                showFabs = !showFabs
            }
        }
    }

    private fun setMyTheme(theme: Int) {
        themeStorage.theme = theme
        requireActivity().recreate()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? MainActivity)?.mainSubcomponent?.inject(this)
    }


}

class SettingsViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel() as T
    }
}