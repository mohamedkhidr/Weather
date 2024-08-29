package com.khidrew.myweather.repositories

import com.khidrew.myweather.models.DailyForecasts
import com.khidrew.myweather.models.HourlyForecast
import com.khidrew.myweather.models.Location
import retrofit2.Response
import com.khidrew.myweather.models.Result
import com.khidrew.myweather.network.Api

class WeatherRepoImpl(private val api:Api) : WeatherRepo {
    override suspend fun searchLocation(query: String): Result<List<Location>> {
      return request {
          api.searchLocation(query = query)
      }
    }

    override suspend fun getDailyForecast(locationKey: String): Result<DailyForecasts> {
        return request {
            api.getDailyForecasts(locationKey = locationKey)
        }
    }

    override suspend fun getHourlyForecast(locationKey: String): Result<List<HourlyForecast>> {
        return request {
            api.getHourlyForecasts(locationKey = locationKey)
        }
    }


    private suspend fun <T> request(request: suspend () -> Response<T>): Result<T> {
        try {
            request().also {
                return if(it.isSuccessful){
                    Result.Success(it.body()!!)
                }else{
                    Result.Error(it.errorBody()?.string().toString())
                }
            }
        }catch (ex:Exception){
            return Result.Error(ex.message.toString())
        }
    }
}