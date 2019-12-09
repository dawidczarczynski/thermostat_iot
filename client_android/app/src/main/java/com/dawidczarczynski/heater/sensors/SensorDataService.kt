package com.dawidczarczynski.heater.sensors

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.dawidczarczynski.heater.config.SocketEvents
import com.dawidczarczynski.heater.config.UrlConstants
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class SensorDataService : Service() {

    private val socket: Socket = IO.socket(UrlConstants.SOCKET_HOST.url)

    override fun onCreate() {
        super.onCreate()
        socket
            .connect()
            .on(Socket.EVENT_CONNECT) { Log.v(TAG,"socket connected") }
            .on(Socket.EVENT_DISCONNECT) { Log.v(TAG,"socket disconnected") }
            .on(SocketEvents.TEMPERATURE_CHANGE.event) { args ->
                val temperatureSample = args[0] as JSONObject
                broadcastTemperatureSample(temperatureSample)
                Log.v(TAG, "Received temperature change event: $temperatureSample")
            }

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
        socket.off(SocketEvents.TEMPERATURE_CHANGE.event)
        socket.disconnect()
    }

    private fun handleListenForSensor(sensorId: String) {
        Log.v(TAG, "listening for sensor $sensorId")

        socket.emit(SocketEvents.LISTEN_FOR_SENSOR.event, sensorId)
    }

    private fun broadcastTemperatureSample(temperatureSample: JSONObject) {
        val temperatureSampleIntent = Intent(TEMPERATURE_SAMPLE).apply {
            this.putExtra(SENSOR_ID, temperatureSample.getString("id"))
            this.putExtra(TEMPERATURE, temperatureSample.getString("temperature"))
        }

      sendBroadcast(temperatureSampleIntent)
    }

    companion object {
        const val TAG = "SensorDataService"
        const val TEMPERATURE_SAMPLE = "com.dawidczarczynski.heater.TEMPERATURE_SAMPLE"
        const val SENSOR_ID = "sensor_id"
        const val TEMPERATURE = "temperature"
    }
}
