package com.dawidczarczynski.heater.heater

class HeaterManager {

    private var heaterStatus: Boolean? = null

    fun setHeaterStatus(status: Boolean) {
        heaterStatus = status
    }

    fun shouldStatusBeChanged(setTemperature: Double, sensorTemperature: Double): Boolean {
       return getNextStatus(setTemperature, sensorTemperature) != heaterStatus
    }

    fun getNextStatus(setTemperature: Double, sensorTemperature: Double): Boolean {
        return sensorTemperature < setTemperature
    }

}