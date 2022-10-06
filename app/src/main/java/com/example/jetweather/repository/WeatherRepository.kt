package com.example.jetweather.repository

import android.util.Log
import com.example.jetweather.data.DataOrException
import com.example.jetweather.model.Weather
import com.example.jetweather.network.WeatherAPI
import retrofit2.http.Query
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherAPI: WeatherAPI) {

    suspend fun getWeather(cityQuery: String):DataOrException<Weather,Boolean,Exception>{
        val response = try{
            weatherAPI.getWeather(query = cityQuery)
        }catch(e:Exception){
            Log.d("Excep","getWeather: ${e}")
            return DataOrException(e = e)
        }
        Log.d("INSIDE","getWeather: $response")
        return DataOrException(data = response)
    }
}