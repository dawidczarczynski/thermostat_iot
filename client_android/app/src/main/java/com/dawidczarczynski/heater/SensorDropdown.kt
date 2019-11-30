package com.dawidczarczynski.heater

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.dawidczarczynski.heater.utils.HttpClient
import com.google.gson.Gson

class SensorDropdown : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_sensor_dropdown, container, false)
        spinner = view.findViewById(R.id.sensorSpinner)
        setDropdownBehavior()
        getSensorsList()
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun setDropdownContent(sensorList: Array<String>) {
        spinner.adapter = ArrayAdapter<String>(
            activity!!.applicationContext,
            R.layout.sensor_spinner_item,
            R.id.sensorSpinnerItem,
            sensorList
        )
    }

    private fun setDropdownBehavior() {
           spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.v("item", parent.getItemAtPosition(position) as String)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub
            }
        }
    }

    private fun getSensorsList() {
        HttpClient(context!!).get(
            "http://10.0.2.2:3000/sensors",
            {
                val sensorList = Gson().fromJson(it, Array<String>::class.java)
                setDropdownContent(sensorList)
            },
            null
        )
    }

    interface OnFragmentInteractionListener {
        // TODO: Create interaction interface
    }

}
