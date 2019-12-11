package com.dawidczarczynski.heater.sensors

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.dawidczarczynski.heater.config.SocketEvents
import com.dawidczarczynski.heater.utils.WebsocketClient
import org.json.JSONObject

class SensorCommunicationService : Service() {

    override fun onCreate() {
        super.onCreate()
        WebsocketClient.registerEventHandler(
            SocketEvents.TEMPERATURE_CHANGE.event
        ) { broadcastTemperatureSample(it) }

        Log.v(TAG, "created")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        intent.getStringExtra(SENSOR_ID).also {
            handleListenForSensor(it!!)
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        WebsocketClient.unregisterAllEventHandlers()
    }

    private fun handleListenForSensor(sensorId: String) {
        Log.v(TAG, "listening for sensor $sensorId")
        WebsocketClient.sendEvent(SocketEvents.LISTEN_FOR_SENSOR.event, sensorId)
    }

    private fun broadcastTemperatureSample(temperatureSample: JSONObject) {
        val temperatureSampleIntent = Intent(TEMPERATURE_SAMPLE).apply {
            this.putExtra(SENSOR_ID, temperatureSample.getString("id"))
            this.putExtra(TEMPERATURE, temperatureSample.getString("temperature"))
        }

      sendBroadcast(temperatureSampleIntent)
    }

    companion object {
        const val TAG = "SensorCommunicationService"
        const val TEMPERATURE_SAMPLE = "com.dawidczarczynski.heater.TEMPERATURE_SAMPLE"
        const val SENSOR_ID = "sensor_id"
        const val TEMPERATURE = "temperature"
    }
}
