package com.dawidczarczynski.heater.sensors

import android.app.IntentService
import android.content.Intent
import com.dawidczarczynski.heater.config.UrlConstants
import com.dawidczarczynski.heater.utils.HttpClient

const val GET_ALL_SENSORS = "com.dawidczarczynski.heater.GET_ALL_SENSORS"
const val GET_ALL_SENSORS_ERROR = "com.dawidczarczynski.heater.GET_ALL_SENSORS_ERROR"
const val SENSORS_LIST = "com.dawidczarczynski.heater.SENSORS_LIST"
const val SENSORS = "sensors"
private const val TAG = "SensorCrudService"

class SensorCrudService : IntentService(TAG) {

    private val httpClient = HttpClient(applicationContext)

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            GET_ALL_SENSORS -> getAllSensors()
        }
    }

    private fun getAllSensors() {
        httpClient.get(
            UrlConstants.HOST.url + UrlConstants.SENSORS_LIST.url,
            SensorList::class.java,
            {
                val sensorsIntent = Intent(SENSORS_LIST).apply {
                    this.putExtra(SENSORS, it)
                }
                sendBroadcast(sensorsIntent)
            },
            {
                val sensorsErrorIntent = Intent(GET_ALL_SENSORS_ERROR)
                sendBroadcast(sensorsErrorIntent)
            }
        )
    }

}
