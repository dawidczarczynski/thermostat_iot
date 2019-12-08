package com.dawidczarczynski.heater

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.dawidczarczynski.heater.sensors.Sensor
import com.dawidczarczynski.heater.sensors.SensorDataService
import com.dawidczarczynski.heater.sensors.SensorDataService.Companion.SENSOR_ID
import com.dawidczarczynski.heater.sensors.SensorDropdown
import com.sdsmdg.harjot.crollerTest.Croller

class MainActivity : AppCompatActivity(), SensorDropdown.OnFragmentInteractionListener {

    private lateinit var heaterController: Croller
    private lateinit var temperatureLabel: TextView
    private lateinit var sensorTemperatureLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        heaterController = findViewById(R.id.heaterController)
        temperatureLabel = findViewById(R.id.temperatureLabel)
        sensorTemperatureLabel = findViewById(R.id.sensorTemperatureLabel)

        heaterController.setOnProgressChangedListener{
            val celsiusDegrees = "$it°"
            temperatureLabel.text = celsiusDegrees
        }
    }

    override fun onSensorSelected(sensor: Sensor) {
        Log.v(TAG, "Sensor selected: $sensor")

        val intent = Intent(this, SensorDataService::class.java)
        intent.putExtra(SENSOR_ID, sensor.id)

        startService(intent)
    }

    companion object {
        const val TAG = "MainActivity"
    }

}
