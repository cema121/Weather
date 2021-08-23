package com.hungryshark.weather.repository

import com.hungryshark.weather.model.Weather
import com.hungryshark.weather.room.HistoryDao
import com.hungryshark.weather.utils.convertHistoryEntityToWeather
import com.hungryshark.weather.utils.convertWeatherToEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository {
    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }
}