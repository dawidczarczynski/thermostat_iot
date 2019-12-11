package com.dawidczarczynski.heater.heater

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.dawidczarczynski.heater.config.SocketEvents
import com.dawidczarczynski.heater.config.UrlConstants
import com.dawidczarczynski.heater.utils.HttpClient
import com.dawidczarczynski.heater.utils.WebsocketClient
import org.json.JSONObject

const val SET_TEMPERATURE = "set_temperature"
const val SENSOR_TEMPERATURE = "sensor_temperature"
const val HEATER_STATUS = "heaterStatus"
private const val TAG = "HeaterService"

class HeaterService : Service() {

    private val heaterManager = HeaterManager()

    override fun onCreate() {
        super.onCreate()
        WebsocketClient.registerEventHandler(SocketEvents.HEATER_STATUS_CHANGE.event) {
            Log.v(TAG, "heater status changed: $it")
            val heaterStatus = it.getBoolean(HEATER_STATUS)
            heaterManager.setHeaterStatus(heaterStatus)
        }
        WebsocketClient.sendEvent(SocketEvents.LISTEN_FOR_HEATER_STATUS.event, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        WebsocketClient.unregisterEventHandler(SocketEvents.HEATER_STATUS_CHANGE.event)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val sensorTemperature = intent.getDoubleExtra(SENSOR_TEMPERATURE, 0.0)
        val setTemperature = intent.getDoubleExtra(SET_TEMPERATURE, 0.0)

        manageHeaterState(sensorTemperature, setTemperature)

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun manageHeaterState(sensorTemperature: Double, setTemperature: Double) {
        if (heaterManager.shouldStatusBeChanged(setTemperature, sensorTemperature)) {
            val nextStatus = heaterManager.getNextStatus(setTemperature, sensorTemperature)
            changeHeaterStatus(nextStatus)
        }
    }

    private fun changeHeaterStatus(status: Boolean) {
        val payload = JSONObject().apply {
            this.put(HEATER_STATUS, status)
        }

        HttpClient(applicationContext).post(
            UrlConstants.HOST.url + UrlConstants.HEATER.url,
            payload,
            {},
            null
        )
    }

}
