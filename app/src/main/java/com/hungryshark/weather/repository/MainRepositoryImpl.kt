package com.hungryshark.weather.repository

import com.hungryshark.weather.model.Weather
import com.hungryshark.weather.model.getRussianCities
import com.hungryshark.weather.model.getWorldCities

class MainRepositoryImpl : MainRepository {
    override fun getWeatherFromServer() = Weather()

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}