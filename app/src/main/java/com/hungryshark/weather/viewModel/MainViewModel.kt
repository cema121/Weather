package com.hungryshark.weather.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hungryshark.weather.app.AppState
import com.hungryshark.weather.repository.MainRepository
import com.hungryshark.weather.repository.MainRepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<Any> = MutableLiveData(),
    private val repositoryImpl: MainRepository = MainRepositoryImpl()
) :
    ViewModel() {
    fun getLiveData() = liveDataToObserve
    fun getWeatherFromLocalSourceRus() = requestDataFromLocalSource(isRussian = true)
    fun getWeatherFromLocalSourceWorld() = requestDataFromLocalSource(isRussian = false)
    fun getWeatherFromRemoteSource() = requestDataFromLocalSource(isRussian = true)

    private fun requestDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            liveDataToObserve.postValue(
                AppState.Success(
                    if (isRussian) {
                        repositoryImpl.getWeatherFromLocalStorageRus()
                    } else {
                        repositoryImpl.getWeatherFromLocalStorageWorld()
                    }
                )
            )
        }.start()
    }
}