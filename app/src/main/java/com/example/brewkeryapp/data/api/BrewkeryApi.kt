package com.example.brewkeryapp.data.api

import com.example.brewkeryapp.data.models.MenuItem
import com.example.brewkeryapp.data.models.MenuResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface BrewkeryApi {
    @GET("data.json")
    suspend fun getMenu(): MenuResponse

    @GET("api/items/{id}.json")
    suspend fun getItemById(@Path("id") id: Int): MenuItem

    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/VivekShah138/Brewkery/main/"

        fun create(): BrewkeryApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BrewkeryApi::class.java)
        }
    }
}
