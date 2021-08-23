package com.hungryshark.weather.repository

import com.hungryshark.weather.model.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}