package com.dawidczarczynski.heater.sensors

import java.io.Serializable

class SensorList : Serializable {

    private val serialVersionUID = 1L
    var sensors: List<Sensor> = ArrayList()

}