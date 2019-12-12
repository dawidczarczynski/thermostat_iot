package com.dawidczarczynski.heater

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import android.widget.TextView
import com.dawidczarczynski.heater.heater.*
import com.dawidczarczynski.heater.sensors.Sensor
import com.dawidczarczynski.heater.sensors.SensorCommunicationService
import com.dawidczarczynski.heater.sensors.SensorCommunicationService.Companion.SENSOR_ID
import com.dawidczarczynski.heater.sensors.SensorCommunicationService.Companion.TEMPERATURE
import com.dawidczarczynski.heater.sensors.SensorCommunicationService.Companion.TEMPERATURE_SAMPLE
import com.dawidczarczynski.heater.sensors.SensorDropdown
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sdsmdg.harjot.crollerTest.Croller

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), SensorDropdown.OnFragmentInteractionListener {

    private var intentReceiver: BroadcastReceiver? = null

    private var setTemperature: Double? = null
    private var sensorTemperature: Double? = null
    private var heaterStatus: Boolean? = null

    private lateinit var temperatureLabel: TextView
    private lateinit var sensorTemperatureLabel: TextView
    private lateinit var heaterController: Croller
    private lateinit var heaterStatusSwitch: Switch
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureIntentReceiver()

        temperatureLabel = findViewById(R.id.temperatureLabel)
        sensorTemperatureLabel = findViewById(R.id.sensorTemperatureLabel)
        heaterController = findViewById(R.id.heaterController)
        heaterStatusSwitch = findViewById(R.id.heaterStatusSwitch)

        bottomNav = findViewById(R.id.navigation)

        heaterController.setOnProgressChangedListener{
            val celsiusDegrees = "$it°"
            temperatureLabel.text = celsiusDegrees
            setTemperature = it.toDouble()
            sendTemperatureConfigIntent()
        }

        bottomNav.setOnNavigationItemSelectedListener {
            var activityClass: Class<*>? = null

            when (it.itemId) {
                R.id.heater_control_item -> {
                    activityClass = MainActivity::class.java
                }
                R.id.sensors_item -> {
                    activityClass = SensorsListActivity::class.java
                }
            }

            if (activityClass != this.javaClass)
                Intent(this, activityClass)
                    .also {intent -> startActivity(intent)}

            false
        }
    }

    private fun configureIntentReceiver() {
        intentReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
              when (intent.action) {
                  TEMPERATURE_SAMPLE -> {
                      sensorTemperature = intent.getStringExtra(TEMPERATURE)?.toDouble()
                      handleIncomingSensorSample()
                  }
                  HEATER_STATUS_CHANGE -> {
                      heaterStatus = intent.getBooleanExtra(HEATER_STATUS, false)
                      heaterStatusSwitch.isChecked = heaterStatus!!
                      Log.v(TAG, "Heater status received: $heaterStatus")
                  }
              }
            }
        }

        IntentFilter()
            .apply {
                this.addAction(TEMPERATURE_SAMPLE)
                this.addAction(HEATER_STATUS_CHANGE)
            }
           .also {
               registerReceiver(intentReceiver, it)
           }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(intentReceiver)
    }

    override fun onSensorSelected(sensor: Sensor) {
        Log.v(TAG, "Sensor selected: $sensor")

        Intent(this, SensorCommunicationService::class.java)
            .apply { putExtra(SENSOR_ID, sensor.id) }
            .also { startService(it) }
    }

    private fun handleIncomingSensorSample() {
        if (sensorTemperature != null) {
            showSensorTemperature()
            sendTemperatureConfigIntent()
        }

        Log.v(TAG, "Temperature sample received: $sensorTemperature")
    }

    private fun sendTemperatureConfigIntent() {
        if (setTemperature != null && sensorTemperature != null) {

            Log.v(TAG, "Sending temperature config intent")

            Intent(this, HeaterService::class.java)
                .apply {
                    putExtra(SET_TEMPERATURE, setTemperature!!)
                    putExtra(SENSOR_TEMPERATURE, sensorTemperature!!)
                }
                .also {
                    startService(it)
                }
        }
    }

    private fun showSensorTemperature() {
        val celsiusDegrees = "$sensorTemperature°"
        sensorTemperatureLabel.text = celsiusDegrees
    }

}
