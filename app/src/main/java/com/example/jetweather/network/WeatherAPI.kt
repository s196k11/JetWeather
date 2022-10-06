package com.example.jetweather.network

import com.example.jetweather.model.Weather
import com.example.jetweather.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface WeatherAPI {

    @GET(value = "v1/current.json")
    suspend fun getWeather(
        @Query("key") key: String = Constants.API_KEY,
        @Query("q") query: String
    ): Weather

}
