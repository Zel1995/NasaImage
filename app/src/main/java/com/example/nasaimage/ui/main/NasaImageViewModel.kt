package com.example.nasaimage.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaimage.data.repository.Success
import com.example.nasaimage.data.storage.ThemeStorage
import com.example.nasaimage.domain.model.Nasa
import com.example.nasaimage.domain.usecase.NasaDataInteractor
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NasaImageViewModel (private val interactor: NasaDataInteractor): ViewModel() {

    private val _loading = MutableStateFlow<Boolean>(false)
    private val _error = MutableSharedFlow<String>()
    private val _nasa = MutableStateFlow<Nasa?>(null)

    val loading =_loading
    val error = _error
    val nasa = _nasa

    fun fetchNasa(date :Int){
        viewModelScope.launch {
            _loading.value = true
            val result = interactor.getNasaInfoByDay(date)
            when(result){
                is Success ->{
                    _nasa.value = result.value
                }
                is Error ->{
                    _error.emit(result.printStackTrace().toString())
                }
            }
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}