package com.example.nasaimage.data.network

import com.example.nasaimage.BuildConfig
import com.example.nasaimage.data.model.NasaResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("/planetary/apod?")
    suspend fun getInfo(
        @Query("date") date: String,
        @Query("api_key") apiKey: String = BuildConfig.NASA_KEY
    ): NasaResponse
}
