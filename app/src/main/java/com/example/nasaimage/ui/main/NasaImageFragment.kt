package com.example.nasaimage.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.nasaimage.BuildConfig
import com.example.nasaimage.R
import com.example.nasaimage.data.di.App
import com.example.nasaimage.data.network.NasaApi
import com.example.nasaimage.data.repository.RepositoryImpl
import com.example.nasaimage.databinding.NasaImageFragmentBinding
import com.example.nasaimage.domain.usecase.NasaDataInteractor
import com.example.nasaimage.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NasaImageFragment : Fragment(R.layout.nasa_image_fragment) {
    companion object {
        const val NASA_TODAY = 0
        const val NASA_YESTERDAY = -1
        const val NASA_BEFORE_YESTERDAY = -2
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
//Не инициализируется фэктори ,не могу понять в чем проблема
    //@Inject
    //lateinit var factory: NasaImageViewModelFactory
    var factory: NasaImageViewModelFactory = NasaImageViewModelFactory(
        NasaDataInteractor(
            RepositoryImpl(
                Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(NasaApi::class.java)
            )
        )
    )

    private val viewBinding: NasaImageFragmentBinding by viewBinding(NasaImageFragmentBinding::bind)
    private val viewModel: NasaImageViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as? App)?.appComponent?.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(viewBinding.bottomBehaviorContainer)

        initViewModel()
        initChpisListener()
        initListeners()
        viewModel.fetchNasa(NASA_TODAY)
    }

    private fun initListeners() {
        with(viewBinding) {
            infoBtn.setOnClickListener {
                showBottomSheetBehavior()
            }
            textInputLayout.setEndIconOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data =
                        Uri.parse("https://en.wikipedia.org/wiki/${textInputEditText.text.toString()}")
                })
                textInputEditText.clearFocus()
                textInputEditText.setText("")
            }
        }
    }

    private fun showBottomSheetBehavior() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun initChpisListener() {
        viewBinding.yesterday.setOnClickListener {
            viewModel.fetchNasa(NASA_YESTERDAY)
        }
        viewBinding.today.setOnClickListener {
            viewModel.fetchNasa(NASA_TODAY)
        }

        viewBinding.beforeYesterday.setOnClickListener {
            viewModel.fetchNasa(NASA_BEFORE_YESTERDAY)
        }
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.nasa.collect {
                    with(viewBinding) {
                        title.text = it?.title
                        explanation.text = it?.explanation
                        Glide.with(nasaImg)
                            .load(it?.hdUrl)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(nasaImg)

                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loading.collect {
                   with(viewBinding){
                       if(it){
                           nasaLayout.visibility = View.GONE
                           progress.visibility = View.VISIBLE
                       } else {
                           nasaLayout.visibility = View.VISIBLE
                           progress.visibility = View.GONE
                       }
                   }

                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}

class NasaImageViewModelFactory @Inject constructor(private val interactor: NasaDataInteractor) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NasaImageViewModel::class.java)) {
            return NasaImageViewModel(interactor) as T
        } else {
            throw Exception("miss view model")
        }
    }
}