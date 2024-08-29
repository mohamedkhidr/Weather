package com.khidrew.myweather.repositories

import com.khidrew.myweather.models.DailyForecasts
import com.khidrew.myweather.models.HourlyForecast
import com.khidrew.myweather.models.Location
import com.khidrew.myweather.models.Result

interface WeatherRepo {

    suspend fun searchLocation(query:String): Result<List<Location>>
    suspend fun getDailyForecast(locationKey: String) : Result<DailyForecasts>

    suspend fun getHourlyForecast(locationKey: String) : Result<List<HourlyForecast>>
}