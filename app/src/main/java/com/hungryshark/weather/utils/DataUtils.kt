package com.hungryshark.weather.utils

import com.hungryshark.weather.model.FactDTO
import com.hungryshark.weather.model.Weather
import com.hungryshark.weather.model.WeatherDTO
import com.hungryshark.weather.model.getDefaultCity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(
        Weather(
            getDefaultCity(), fact.temp!!, fact.feels_like!!,
            fact.condition!!, fact.icon
        )
    )
}