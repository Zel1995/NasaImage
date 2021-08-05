package com.example.nasaimage.domain.usecase

import com.example.nasaimage.data.repository.RepositoryResult
import com.example.nasaimage.domain.model.Nasa
import com.example.nasaimage.domain.repository.Repository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NasaDataInteractor @Inject constructor(private val repository: Repository) {
    suspend fun getNasaInfoByDay(date: Int): RepositoryResult<Nasa> {
        return repository.getNasaInfo(getDate(date))
    }

    private fun getDate(date: Int): String {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, date)
        return dateFormatter.format(cal.time).toString()
    }
}