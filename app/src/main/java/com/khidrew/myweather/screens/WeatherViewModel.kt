package com.khidrew.myweather.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khidrew.myweather.models.DailyForecasts
import com.khidrew.myweather.models.HourlyForecast
import com.khidrew.myweather.models.Result
import com.khidrew.myweather.repositories.WeatherRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherViewModel : ViewModel(), KoinComponent {
    val repo: WeatherRepo by inject()


    private val _hourlyForecast: MutableStateFlow<Result<List<HourlyForecast>>?> =
        MutableStateFlow(null)
    val hourlyForecast = _hourlyForecast.asStateFlow()
    private val _dailyForecasts: MutableStateFlow<Result<DailyForecasts>?> = MutableStateFlow(null)
    val dailyForecasts = _dailyForecasts.asStateFlow()


    fun getHourlyForecast(locationKey: String) {
        viewModelScope.launch {
            _hourlyForecast.update { Result.Loading }
            repo.getHourlyForecast(locationKey).also { data ->
                _hourlyForecast.update { data }
            }
        }
    }

    fun getDailyForecast(locationKey: String) {
        viewModelScope.launch {
            _dailyForecasts.update { Result.Loading }
            repo.getDailyForecast(locationKey).also { data ->
                _dailyForecasts.update { data }
            }
        }
    }

}