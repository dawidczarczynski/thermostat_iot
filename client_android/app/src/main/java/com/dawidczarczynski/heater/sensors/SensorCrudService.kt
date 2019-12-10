package com.dawidczarczynski.heater.sensors

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dawidczarczynski.heater.config.UrlConstants
import com.dawidczarczynski.heater.utils.HttpClient
import android.os.Bundle
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



const val GET_ALL_SENSORS = "com.dawidczarczynski.heater.GET_ALL_SENSORS"
const val GET_ALL_SENSORS_ERROR = "com.dawidczarczynski.heater.GET_ALL_SENSORS_ERROR"
const val SENSORS_LIST = "com.dawidczarczynski.heater.SENSORS_LIST"
const val SENSORS = "sensors"
private const val TAG = "SensorCrudService"

class SensorCrudService : IntentService(TAG) {

    private var httpClient: HttpClient? = null

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        httpClient = HttpClient(base!!)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.v(TAG, "Received intent ${intent?.action}")
        when (intent?.action) {
            GET_ALL_SENSORS -> getAllSensors()
        }
    }

    private fun getAllSensors() {
        httpClient?.get(
            UrlConstants.HOST.url + UrlConstants.SENSORS_LIST.url,
            SensorList::class.java,
            {
               Intent(SENSORS_LIST).apply {
                    this.putExtra(SENSORS, it)
                }.also {
                    sendBroadcast(it)
                }
            },
            {
                Intent(GET_ALL_SENSORS_ERROR).also {
                    sendBroadcast(it)
                }
            }
        )
    }

}
