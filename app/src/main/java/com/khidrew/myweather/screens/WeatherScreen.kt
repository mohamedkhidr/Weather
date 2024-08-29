package com.khidrew.myweather.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.khidrew.myweather.models.Result
import com.khidrew.myweather.ui.theme.russoFont
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun WeatherScreen (
navController: NavController,
locationKey:String,
locationName:String,
country: String,
viewModel: WeatherViewModel = viewModel()
){

    val dailyForecasts by viewModel.dailyForecasts.collectAsState()
    val hourlyForecasts by viewModel.hourlyForecast.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.getDailyForecast(locationKey)
        viewModel.getHourlyForecast(locationKey)
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(modifier = Modifier.padding(vertical = 32.dp)) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = Color.White,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = locationName,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )

                Text(
                    text = country,
                    color = Color.Gray,
                )
            }

        }
        AnimatedVisibility(visible = hourlyForecasts is Result.Success) {
            val data = hourlyForecasts as Result.Success
            val temp = data.data.first().temperature
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "${temp.value}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 80.sp,
                    color = Color.White,
                    fontFamily = russoFont
                )
            }
        }
        AnimatedVisibility(visible = hourlyForecasts is Result.Loading) {
            Loading()
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Hourly forecasts",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(10.dp))
        AnimatedVisibility(visible = hourlyForecasts is Result.Success) {
            val data = hourlyForecasts as Result.Success
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(data.data) { forecast ->
                    Column(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .size(100.dp, 140.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.secondary),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = SimpleDateFormat("H a").format(Date(forecast.epochDataTime * 1000)),
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        AsyncImage(
                            modifier = Modifier.weight(3f),
                            model = ImageRequest.Builder(
                                LocalContext.current
                            )
                                .data("https://developer.accuweather.com/sites/default/files/${forecast.weatherIcon.fixIcon()}-s.png")
                                .build(),
                            contentScale = ContentScale.FillWidth,
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            modifier = Modifier.weight(1f),
                            text = forecast.temperature.value.toString(),
                            color = Color.White
                        )
                    }
                }
            }
        }
        AnimatedVisibility(visible = hourlyForecasts is Result.Loading) {
            Loading()
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Daily forecasts",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(10.dp))
        AnimatedVisibility(visible = dailyForecasts is Result.Success) {
            val data = dailyForecasts as Result.Success
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(data.data.dailyForecasts) { forecast ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.secondary)
                            .padding(start = 16.dp, end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${SimpleDateFormat("d").format(Date(forecast.epochDate * 1000))}",
                            color = Color.White
                        )
                        Row {
                            Icon(
                                Icons.Default.ArrowDownward,
                                tint = Color(0xffff5353),
                                contentDescription = null
                            )
                            Text(
                                text = "${forecast.temperature.minimum.value}°",
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Icon(
                                Icons.Default.ArrowUpward,
                                tint = Color(0xff2eff8c),
                                contentDescription = null
                            )
                            Text(
                                text = "${forecast.temperature.maximum.value}°",
                                color = Color.White
                            )
                        }

                        AsyncImage(
                            modifier = Modifier.size(70.dp),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://developer.accuweather.com/sites/default/files/${forecast.day.icon.fixIcon()}-s.png").build(),
                            contentDescription = "",
                            contentScale = ContentScale.Fit
                        )

                    }
                }
            }
        }
        AnimatedVisibility(visible = dailyForecasts is Result.Loading) {
            Loading()
        }


    }

}


@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color.White)
    }
}

fun Int.fixIcon(): String {
    return if (this > 9) {
        toString()
    } else {
        "0${this}"
    }
}