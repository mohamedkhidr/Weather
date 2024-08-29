package com.khidrew.myweather.models

import com.google.gson.annotations.SerializedName

data class HourlyForecast(
    @SerializedName("Data")
    val data:String,
    @SerializedName("EpochDateTime")
    val epochDataTime:Long ,
    @SerializedName("WeatherIcon")
    val weatherIcon:Int ,
    @SerializedName("IconPhrase")
    val iconPhrase: String ,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean ,
    @SerializedName("IsDayLight")
    val isDayLight: Boolean ,
    @SerializedName("Temperature")
    val temperature:Value

)
