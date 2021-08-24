package com.example.nasaimage.ui.main.subfragments

import android.os.Bundle
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.nasaimage.MainActivity
import com.example.nasaimage.R
import com.example.nasaimage.databinding.BeforeYesterdayFragmentBinding
import com.example.nasaimage.ui.main.NasaImageFragment
import com.example.nasaimage.ui.main.NasaImageViewModel
import com.example.nasaimage.ui.main.NasaImageViewModelFactory
import com.example.nasaimage.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeforeYesterdayFragment (): Fragment(R.layout.before_yesterday_fragment) {
    private val viewBinding: BeforeYesterdayFragmentBinding by viewBinding(
        BeforeYesterdayFragmentBinding::bind
    )
    private var bool = false

    @Inject
    lateinit var factory: NasaImageViewModelFactory
    private val viewModel: NasaImageViewModel by viewModels { factory }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as? MainActivity)?.mainSubcomponent?.inject(this)
        viewModel.fetchNasa(NasaImageFragment.NASA_BEFORE_YESTERDAY)
        initViewModel()
        animateImageViewScaleType()
    }

    private fun animateImageViewScaleType() {
        viewBinding.beforeYesterdayImg.setOnClickListener{
            bool = !bool
            TransitionManager.beginDelayedTransition(viewBinding.beforeYesterdayContainer,ChangeImageTransform())
            viewBinding.beforeYesterdayImg.scaleType = if(bool) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nasa.collect {
                    Glide.with(viewBinding.beforeYesterdayImg)
                        .load(it?.hdUrl)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(viewBinding.beforeYesterdayImg)
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loading.collect {
                    with(viewBinding){
                        if(it){
                            beforeYesterdayProgress.visibility = View.VISIBLE
                            beforeYesterdayImg.visibility = View.GONE
                        }else{
                            beforeYesterdayProgress.visibility = View.VISIBLE
                            beforeYesterdayImg.visibility = View.GONE
                        }
                    }

                }
            }
        }
    }
}