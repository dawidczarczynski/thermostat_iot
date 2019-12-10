package com.dawidczarczynski.heater

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.dawidczarczynski.heater.sensors.Sensor
import com.dawidczarczynski.heater.sensors.SensorCommunicationService
import com.dawidczarczynski.heater.sensors.SensorCommunicationService.Companion.SENSOR_ID
import com.dawidczarczynski.heater.sensors.SensorCommunicationService.Companion.TEMPERATURE
import com.dawidczarczynski.heater.sensors.SensorCommunicationService.Companion.TEMPERATURE_SAMPLE
import com.dawidczarczynski.heater.sensors.SensorDropdown
import com.sdsmdg.harjot.crollerTest.Croller

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), SensorDropdown.OnFragmentInteractionListener {

    private var temperatureSampleReceiver: BroadcastReceiver? = null

    private lateinit var heaterController: Croller
    private lateinit var temperatureLabel: TextView
    private lateinit var sensorTemperatureLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureIntentReceiver()

        heaterController = findViewById(R.id.heaterController)
        temperatureLabel = findViewById(R.id.temperatureLabel)
        sensorTemperatureLabel = findViewById(R.id.sensorTemperatureLabel)

        heaterController.setOnProgressChangedListener{
            val celsiusDegrees = "$it°"
            temperatureLabel.text = celsiusDegrees
        }
    }

    private fun configureIntentReceiver() {
        temperatureSampleReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val temperature = intent.getStringExtra(TEMPERATURE)
                if (temperature != null)
                    showSensorTemperature(temperature)

                Log.v(TAG, "Temperature sample received: $temperature")
            }
        }
        val filter = IntentFilter(TEMPERATURE_SAMPLE)
        registerReceiver(temperatureSampleReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(temperatureSampleReceiver)
    }

    override fun onSensorSelected(sensor: Sensor) {
        Log.v(TAG, "Sensor selected: $sensor")

        Intent(this, SensorCommunicationService::class.java)
            .apply { putExtra(SENSOR_ID, sensor.id) }
            .also { startService(it) }
    }

    private fun showSensorTemperature(temperature: String) {
        val celsiusDegrees = "$temperature°"
        sensorTemperatureLabel.text = celsiusDegrees
    }

}
