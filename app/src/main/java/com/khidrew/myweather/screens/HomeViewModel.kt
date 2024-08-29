package com.khidrew.myweather.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khidrew.myweather.models.Location
import com.khidrew.myweather.repositories.WeatherRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.khidrew.myweather.models.Result

class HomeViewModel: ViewModel(), KoinComponent {
    private val weatherRepo:WeatherRepo by inject()


    private val _locations:MutableStateFlow<Result<List<Location>>?>  = MutableStateFlow(null)
    val locations  = _locations.asStateFlow()


    fun searchLocation(query:String){
        viewModelScope.launch {
            _locations.update {
                Result.Loading
            }
            weatherRepo.searchLocation(query).also {data ->
                _locations.update { data }
            }
        }
    }
}