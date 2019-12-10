package com.dawidczarczynski.heater.heater

class HeaterManager {

    fun shouldBeTurnedOn(setTemperature: Double, sensorTemperature: Double): Boolean {
        return sensorTemperature < setTemperature
    }

}