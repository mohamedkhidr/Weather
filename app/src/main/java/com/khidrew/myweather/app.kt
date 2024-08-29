package com.khidrew.myweather

import android.app.Application
import com.khidrew.myweather.network.Api
import com.khidrew.myweather.network.HeaderInterceptor
import com.khidrew.myweather.repositories.WeatherRepo
import com.khidrew.myweather.repositories.WeatherRepoImpl
import okhttp3.OkHttpClient
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sin

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module{
                single {
                    val client = OkHttpClient.Builder()
                        .addInterceptor(HeaderInterceptor())
                        .build()
                    Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://dataservice.accuweather.com")
                        .client(client)
                        .build()
                }

                single {
                    val retrofit:Retrofit = get()
                    retrofit.create((Api::class.java))
                }

                single {
                    val api:Api = get()
                    WeatherRepoImpl(api)
                } bind WeatherRepo::class
            })
        }
    }
}