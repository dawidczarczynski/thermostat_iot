package com.dawidczarczynski.heater.sensors

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.dawidczarczynski.heater.R

private const val TAG = "SensorDropdown"

class SensorDropdown : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private var sensorDataReceiver: BroadcastReceiver? = null

    private lateinit var spinner: Spinner
    private lateinit var sensorsError: TextView
    private lateinit var sensorsFetchLoader: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(
            R.layout.fragment_sensor_dropdown,
            container,
            false
        )

        spinner = view.findViewById(R.id.sensorSpinner)
        sensorsError = view.findViewById(R.id.sensorsError)
        sensorsFetchLoader = view.findViewById(R.id.sensorsProgressBar)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            configureIntentReceiver()
            setDropdownBehavior()
            getSensorsList()
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun configureIntentReceiver() {
        sensorDataReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    SENSORS_LIST -> {
                        val sensorsList = intent.getSerializableExtra(SENSORS) as SensorList
                        setDropdownContent(sensorsList.sensors)
                        changeLayoutVisibilityOnSuccess()
                    }
                    GET_ALL_SENSORS_ERROR -> changeLayoutVisibilityOnFailure()
                }
            }
        }
        val filter = IntentFilter(
            GET_ALL_SENSORS_ERROR,
            SENSORS_LIST
        )
        activity?.registerReceiver(sensorDataReceiver, filter)
    }

    private fun setDropdownContent(sensorList: List<Sensor>) {
        spinner.adapter = ArrayAdapter<Sensor>(
            activity!!.applicationContext,
            R.layout.sensor_spinner_item,
            R.id.sensorSpinnerItem,
            sensorList
        )
    }

    private fun setDropdownBehavior() {
           spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedSensor = parent.getItemAtPosition(position) as Sensor
                Log.v(TAG, "Selected sensor: $selectedSensor")

                listener?.onSensorSelected(selectedSensor)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.v(TAG, "nothing selected")
            }
        }
    }

    private fun getSensorsList() {
        changeLayoutVisibilityOnStart()

        Intent(context, SensorCrudService::class.java)
            .apply { action = GET_ALL_SENSORS }
            .also { activity?.startService(it) }
    }

    private fun changeLayoutVisibilityOnStart() {
        sensorsFetchLoader.visibility = View.VISIBLE
    }

    private fun changeLayoutVisibilityOnSuccess() {
        spinner.visibility = View.VISIBLE
        sensorsFetchLoader.visibility = View.INVISIBLE
    }

    private fun changeLayoutVisibilityOnFailure() {
        spinner.visibility = View.INVISIBLE
        sensorsFetchLoader.visibility = View.INVISIBLE
        sensorsError.visibility = View.VISIBLE
    }

    interface OnFragmentInteractionListener {
        fun onSensorSelected(sensor: Sensor)
    }

}
