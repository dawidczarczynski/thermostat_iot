package com.dawidczarczynski.heater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.dawidczarczynski.heater.sensors.SensorDropdown
import com.sdsmdg.harjot.crollerTest.Croller

class MainActivity : AppCompatActivity(), SensorDropdown.OnFragmentInteractionListener {

    private lateinit var heaterController: Croller
    private lateinit var temperatureLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        heaterController = findViewById(R.id.heaterController)
        temperatureLabel = findViewById(R.id.temperatureLabel)

        heaterController.setOnProgressChangedListener{
            val celsiusDegrees = "$it°"
            temperatureLabel.text = celsiusDegrees
        }
    }

}
