package com.dawidczarczynski.heater.sensors

class Sensor {
    var id: String = ""
    var name: String = ""

    override fun toString(): String {
        return name
    }
}
