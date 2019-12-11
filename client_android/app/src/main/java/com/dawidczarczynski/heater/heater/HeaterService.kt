package com.dawidczarczynski.heater.heater

import android.app.IntentService
import android.content.Intent
import android.util.Log

const val SET_TEMPERATURE = "set_temperature"
const val SENSOR_TEMPERATURE = "sensor_temperature"
private const val TAG = "HeaterService"

class HeaterService : IntentService(TAG) {

    private val heaterManager = HeaterManager()

    override fun onHandleIntent(intent: Intent?) {
        val sensorTemperature = intent?.getDoubleExtra(SENSOR_TEMPERATURE, 0.0)
        val setTemperature = intent?.getDoubleExtra(SET_TEMPERATURE, 0.0)

        manageHeaterState(sensorTemperature, setTemperature)
    }

    private fun manageHeaterState(sensorTemperature: Double?, setTemperature: Double?) {
        if (sensorTemperature !== null && setTemperature !== null) {
            if (heaterManager.shouldBeTurnedOn(setTemperature, sensorTemperature))
                Log.v(TAG, "Heater should be turned on")
            else
                Log.v(TAG, "Heater should be turned off")
        }
    }

}
