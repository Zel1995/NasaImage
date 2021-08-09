package com.example.nasaimage.ui.main.subfragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.nasaimage.MainActivity
import com.example.nasaimage.R
import com.example.nasaimage.databinding.YesterdayFragmentBinding
import com.example.nasaimage.ui.main.NasaImageFragment
import com.example.nasaimage.ui.main.NasaImageViewModel
import com.example.nasaimage.ui.main.NasaImageViewModelFactory
import com.example.nasaimage.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class YesterdayFragment:Fragment(R.layout.yesterday_fragment) {
    private val viewBinding: YesterdayFragmentBinding by viewBinding(
        YesterdayFragmentBinding::bind)
    @Inject
    lateinit var factory: NasaImageViewModelFactory
    private val viewModel: NasaImageViewModel by viewModels{factory}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? MainActivity)?.mainSubcomponent?.inject(this)
        viewModel.fetchNasa(NasaImageFragment.NASA_YESTERDAY)
        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nasa.collect {
                    Glide.with(viewBinding.yesterdayImg)
                        .load(it?.hdUrl)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(viewBinding.yesterdayImg)
                }
            }
        }
    }
}