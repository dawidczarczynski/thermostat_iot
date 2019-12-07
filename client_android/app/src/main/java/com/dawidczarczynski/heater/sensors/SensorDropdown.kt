package com.dawidczarczynski.heater.sensors

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.dawidczarczynski.heater.R
import com.dawidczarczynski.heater.utils.HttpClient
import com.google.gson.internal.LinkedTreeMap

class SensorDropdown : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private var sensorService: SensorService? = null

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

        setDropdownBehavior()
        getSensorsList()

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            val httpClient = HttpClient(context)
            sensorService = SensorService(httpClient)
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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
                Log.v("selected sensor", selectedSensor.id)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub
            }
        }
    }

    private fun getSensorsList() {
        changeLayoutVisibilityOnStart()
        sensorService?.getSensorsList(
            {
                Log.v("list selected", it.toString())
                setDropdownContent(it)
                changeLayoutVisibilityOnSuccess()
            },
            {
                changeLayoutVisibilityOnFailure()
            }
        )
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
        // TODO: Create interaction interface
    }

}
