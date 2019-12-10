package com.dawidczarczynski.heater.heater

import android.app.IntentService
import android.content.Intent
import android.util.Log

const val SENSOR_TEMPERATURE = "com.dawidczarczynski.heater.SENSOR_TEMPERATURE"
const val SET_TEMPERATURE = "com.dawidczarczynski.heater.SET_TEMPERATURE"
const val TEMPERATURE = "temperature"

private const val TAG = "HeaterService"

class HeaterService : IntentService(TAG) {

    private val heaterManager = HeaterManager()
    private var sensorTemperature: Double = 0.0
    private var setTemperature: Double = 0.0

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            SENSOR_TEMPERATURE -> sensorTemperature = intent.getDoubleExtra(TEMPERATURE, 0.0)
            SET_TEMPERATURE -> setTemperature = intent.getDoubleExtra(TEMPERATURE, 0.0)
        }

        if (heaterManager.shouldBeTurnedOn(setTemperature, sensorTemperature))
            Log.v(TAG, "Heater should be turned on")
        else
            Log.v(TAG, "Heater should be turned off")
    }

}
