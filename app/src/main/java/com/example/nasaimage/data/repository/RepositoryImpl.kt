package com.example.nasaimage.data.repository

import com.example.nasaimage.data.network.NasaApi
import com.example.nasaimage.domain.model.Nasa
import com.example.nasaimage.domain.repository.Repository

class RepositoryImpl(private val nasaApi: NasaApi) : Repository {
    override suspend fun getNasaInfo(date: String): RepositoryResult<Nasa> {
        try {
            val result = nasaApi.getInfo(date)
            Nasa(
                result.explanation,
                result.hdUrl,
                result.mediaType,
                result.serviceVersion,
                result.title,
                result.url
            ).let { return Success(it) }
        }catch (ex:Exception){
            return  Error(ex)
        }

    }

}

sealed class RepositoryResult<V>()
class Success<T>(val value: T) : RepositoryResult<T>()
class Error<T>(val error: Throwable) : RepositoryResult<T>()