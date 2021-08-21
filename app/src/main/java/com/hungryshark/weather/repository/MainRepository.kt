package com.hungryshark.weather.repository

import com.hungryshark.weather.model.Weather

interface MainRepository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}