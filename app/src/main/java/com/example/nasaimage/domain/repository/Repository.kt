package com.example.nasaimage.domain.repository

import com.example.nasaimage.data.repository.RepositoryResult
import com.example.nasaimage.domain.model.Nasa

interface Repository {
    suspend fun getNasaInfo(date:String): RepositoryResult<Nasa>
}